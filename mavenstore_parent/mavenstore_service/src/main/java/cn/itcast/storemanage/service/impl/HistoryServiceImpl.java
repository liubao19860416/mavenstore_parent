package cn.itcast.storemanage.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cn.itcast.storemanage.dao.HistoryDAO;
import cn.itcast.storemanage.domain.History;
import cn.itcast.storemanage.domain.Store;
import cn.itcast.storemanage.page.Page;
import cn.itcast.storemanage.service.HistoryService;

public class HistoryServiceImpl implements HistoryService {

	private HistoryDAO historyDAO;

	public void setHistoryDAO(HistoryDAO historyDAO) {
		this.historyDAO = historyDAO;
	}

	/**
	 * 根据Page对象,进行条件查询; 查询符合条件的记录,条件没有的话,查询所有记录
	 * 
	 * @param page
	 * @return
	 */
	public void findAllHistoriesByPage(Page<History> page) {

		// 使用hibernate的QBC进行查询;(思路很重要)
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(History.class);// 相当于from History

		// 拼接查询条件(这里使用面向对象的QBC添加限制条件即可)
		Map<String,String> pageMap = page.getParameterMap();

		// 重点： QBC多表查询 --- alias别名
		// 如果不指定连接类型，默认内连接( ca参见笔记记忆);
		detachedCriteria.createAlias("goods", "g", Criteria.INNER_JOIN);

		if (pageMap != null) {

			// JDK6之后,已经对这种+=的字符串拼接实现了优化,底层使用stringbuffer实现;
			// 下面是3个条件的判断和添加;
			//String[] goods_nm = (String[]) parameterMap.get("goods.nm");
			String goods_nm = (String) pageMap.get("goods.nm");
			if (goods_nm != null && goods_nm.length() > 0
					&& StringUtils.isNotBlank(goods_nm)) {
				/**
				 * 需要对其进行URL编码;
				 */
				// try {
				// String nm=URLEncoder.encode(goods_nm[0].trim(), "utf-8");
				// } catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				// }
				detachedCriteria.add(Restrictions.eq("g.nm", goods_nm.trim()));
				
				//System.out.println("执行了条件g.nm"+goods_nm);
				
				
			}
			/**
			 * 这里的中文编码和解码是对应的,这里采用编码的话,当存储该数据的时候,就需要是使用相应的编码存储的,否则无法比较;
			 * 所以一定要存储和比较要编码格式一致
			 */

			String goods_name = (String) pageMap.get("goods.name");
			if (goods_name != null && goods_name.length() > 0
					&& StringUtils.isNotBlank(goods_name)) {
				// try {
				// String name=URLEncoder.encode(goods_name[0].trim(), "utf-8");
				// } catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				// }
				detachedCriteria.add(Restrictions.like("g.name", "%"
						+ goods_name.trim() + "%"));
				
				//System.out.println("执行了条件name:"+goods_name);
			}

			String goods_store_id = (String) pageMap
					.get("goods.store.id");
			if (goods_store_id != null && goods_store_id.length() > 0
					&& StringUtils.isNotBlank(goods_store_id.trim())) {

				/**
				 * 这个不需要进行编码
				 */
				// 这里可以通过面向对象的方式比较;
				// try {
				// String storeid=URLEncoder.encode(goods_store_id[0].trim(),
				// "utf-8");
				// } catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				// }
				Store store = new Store();
				store.setId(goods_store_id.trim());
				detachedCriteria.add(Restrictions.eq("g.store", store));
				
				//System.out.println("执行了条件store"+store);

			}

//			String pageNum = (String) pageMap.get("pageNum");
//			if (pageNum != null && pageNum.length() > 0
//					&& StringUtils.isNotBlank(pageNum)) {
//				// 这里直接将数据封装到page对象中就可以了
//				// 不满足条件就是默认值1;
//				page.setPageNum(Integer.parseInt(pageNum));
//				
//				System.out.println("执行了条件pageNum"+pageNum);
//			}
		}

		// 根据上面的条件查询出结果,封住到page对象中;
		// 需要注意这个时候的结果集封装策略;(现在还没有改变,在查询总记录数的时候发生改变;)
		// 因为在History中包含Goods对象,这个时候需要多表查询,所以下面要对需要访问的多表分别起一个别名;
		detachedCriteria.createAlias("g.store", "s");// 这里没写第三个参数,默认就是上面的额INNER.JOIN;

		// 添加结果集显示的顺序(排序)
		//detachedCriteria.addOrder(Order.asc("datetime"));

		// 封装totalcount,查询总记录数;
		detachedCriteria.setProjection(Projections.rowCount());
		// detachedCriteria.setProjection(Projections.count("id"));

		Long totalCount = historyDAO.findTotalCount(detachedCriteria);
		page.setTotalCount(totalCount);
		// 清除count(*)投影效果(Project);,但是并没有将结果集封装策略恢复为Histoyr对象的(Root.Entity类型),辞职依然为Project类型,只是其为null;
		detachedCriteria.setProjection(null);

		/**
		 * 注释:结果集的封装策略在查询count(*)后为Projections.rowCount(),这是一种Project类型的结果集;
		 * 然后下面将Project类型的结果集设置为null,但是依然是Project类型的,只是其值为null;
		 * 所以这时候仍需要将结果集类型还原为开始的时候的ROOR.ENTITY类型; 即DetachedCriteria
		 * detachedCriteria = DetachedCriteria.forClass(History.class)
		 * 使用语句:detachedCriteria.setResultTransformer(Criteria.ROOT_ENTITY);实现;
		 */
		detachedCriteria.setResultTransformer(Criteria.ROOT_ENTITY);

		// 在这里获取需要的起始角标,和每页显示记录数;
		int firstResult = (page.getPageNum() - 1) * page.getNumberPerPage();
		int maxResults = page.getNumberPerPage();

		// totalPagenum已经在Page对象中自动计算出来封装好了,直接调用get方法就可以获取了;

		// 查询相应的历史记录信息,封装到page对象中;
		List<History> histories = historyDAO.findHistoriesByPage(
				detachedCriteria, firstResult, maxResults);
		
		//System.out.println(histories);
		
		page.setRecords(histories);

	}

	/**
	 * 查询所有的历史记录信息;
	 * 
	 * @return
	 */
	public List<History> findAllHistories() {
		return historyDAO.findAllHistories();
	}

}
