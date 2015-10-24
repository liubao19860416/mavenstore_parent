package cn.itcast.storemanage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import cn.itcast.storemanage.dao.GoodsDAO;
import cn.itcast.storemanage.dao.HistoryDAO;
import cn.itcast.storemanage.domain.Goods;
import cn.itcast.storemanage.domain.History;
import cn.itcast.storemanage.domain.Store;
import cn.itcast.storemanage.page.Page;
import cn.itcast.storemanage.service.GoodsService;

/**
 * 在这个service中,注入了两个DAO,即goodsDAO和historyDAO
 * 
 * @author Administrator
 * 
 */
public class GoodsServiceImpl implements GoodsService {

	private GoodsDAO goodsDAO;

	public void setGoodsDAO(GoodsDAO goodsDAO) {
		this.goodsDAO = goodsDAO;
	}

	private HistoryDAO historyDAO;

	public void setHistoryDAO(HistoryDAO historyDAO) {
		this.historyDAO = historyDAO;
	}

	/**
	 * 根据简约码,查询商品信息,进行自动补全显示使用
	 */
	@Cacheable(value = "goods_ehcache")
	public Goods findGoodsByNm(String nm) {

		return goodsDAO.findByNm(nm);
	}

	/**
	 * 商品入库时的方法,需要判断该商品是否已经存在(更新或者添加),并添加历史记录信息;
	 * 
	 * @param goods
	 * @param name
	 */
	@CacheEvict(value = "goods_ehcache", allEntries = true)
	public void saveGoods(Goods goods, String user) {

		// 判断商品是否存在,只需要对该商品进行更新或者添加操作即可;
		// 更新的时候,需要添加历历史记录和商品中的关联信息storeid;
		if (!StringUtils.isEmpty(goods.getId())) {
			// id存在,说明是已经存在的商品,执行更新数量就行了,所以首先查询获取当前商品数量
			Goods goodsFindById = goodsDAO.findById(goods.getId());// 持久化对象
			// 有可能库存为null了,需要置为0;
			try {
				if (goodsFindById.getAmount() == null) {
					goodsFindById.setAmount(0d);
				}
			} catch (Exception e) {
				goodsFindById.setAmount(0d);
			}
			goodsFindById.setAmount(goods.getAmount()
					+ goodsFindById.getAmount());
			// goodsDAO.save(goodsFindById);//goods由临时状态,成为持久化对象,事务自动提交,不需要执行save方法了
			// goods.setAmount(goods.getAmount()+goodsFindById.getAmount());//托管对象
			// goodsDAO.save(goods);//需要执行save方法

			// History history = new History();
			// history.setAmount(goods.getAmount()-goodsFindById.getAmount());
			// history.setDatetime(new Date().toLocaleString());
			// history.setGoods(goods);
			// // history.setId(id)//id自增长
			// history.setRemain(goods.getAmount());
			// history.setType("0");//更新操作,用0表示;添加操作用1表示;
			// history.setUser(user);

			/**
			 * 添加历史记录产生的语句;首先创建历史记录玄关dao,service和action即相关配置;
			 */

			History history = new History();
			history.setAmount(goods.getAmount());
			history.setDatetime(new Date().toLocaleString());
			history.setGoods(goodsFindById);
			// history.setId(id)//id自增长
			history.setRemain(goodsFindById.getAmount());
			history.setType("1");// 更新操作,添加操作用1表示;出库操作,用0表示;
			history.setUser(user);

			historyDAO.save(history);

		} else {
			// 商品id不存在,说明仓库中不存在,需要执行添加商品的操作;其id会自动生成(uuid策略)
			goodsDAO.save(goods);// goods由临时状态成为持久化状态(事务会自动)

			/**
			 * 添加历史记录产生的语句;首先创建历史记录玄关dao,service和action即相关配置;
			 */
			History history = new History();
			history.setAmount(goods.getAmount());
			history.setDatetime(new Date().toLocaleString());
			history.setGoods(goods);
			// history.setId(id)//id自增长
			history.setRemain(goods.getAmount());
			history.setType("1");// 更新操作,添加操作用1表示;出库操作,用0表示;
			history.setUser(user);

			historyDAO.save(history);
		}

	}

	/**
	 * 查找所有商品
	 * 
	 * @return
	 */
	@Cacheable(value = "goods_ehcache")
	public List<Goods> findAllGoods() {
		return goodsDAO.findAll();
	}

	/**
	 * 商品出库操作的方法(肯定是已经存在的商品了) 和入库操作的一部分相同
	 */
	@CacheEvict(value = "goods_ehcache", allEntries = true)
	public void outGoods(Goods goods, String user) {
		// 之前已经做了校验,所以这里的goods一定是不为空(主要是指id和acount)
		Goods goodsFindById = goodsDAO.findById(goods.getId());// 持久化对象
		// 有可能库存为null了,需要置为0;
		// try{
		// if(goodsFindById.getAmount()==null||goodsFindById.getAmount()==0)
		// {
		// goodsFindById.setAmount(0d);
		// }
		// }catch(Exception e){
		// goodsFindById.setAmount(0d);
		// }
		// 出库操作需要判断数量是否充足!
		if (goodsFindById.getAmount() >= goods.getAmount()) {
			// 说明货物充足
			// 只是出库操作;且是持久化状态;
			goodsFindById.setAmount(goodsFindById.getAmount()
					- goods.getAmount());
			/**
			 * 添加历史记录产生的语句;首先创建历史记录玄关dao,service和action即相关配置;
			 */
			History history = new History();
			history.setAmount(goods.getAmount());
			history.setDatetime(new Date().toLocaleString());
			history.setGoods(goodsFindById);
			// history.setId(id)//id自增长
			history.setRemain(goodsFindById.getAmount());
			history.setType("0");// 更新操作,添加操作用1表示;出库操作,用0表示;
			history.setUser(user);

			historyDAO.save(history);

		} else {
			// 说明货物不足;这个判断可以在action做,应该实现起来更方便;

		}

	}

	/**
	 * 显示所有商品的名称方法 方式二,直接查询获取对应的名字数据的数组
	 */
	@Cacheable(value = "goods_ehcache")
	public List<String> findAllGoodsNames() {
		return goodsDAO.findAllNames();
	}

	/**
	 * 最重要的!!! 显示所有商品的名称方法(ajax异步查询显示联想列表的第二种方式) 方式三,直接查询获取对应的名字的对象(动态实时查询数据库)
	 */
	@Cacheable(value = "goods_ehcache")
	public List<Goods> findSearchedGoods(String name) {
		return goodsDAO.findSearchedGoods(name);
	}

	/**
	 * 条件查询(nm,name和仓库,需要拼接sql语句,且使用like模式查询nm和name)
	 */
	@Cacheable(value = "goods_ehcache")
	public List<Goods> findGoodsByConditions(Map<String, String> map) {
		return goodsDAO.findByConditions(map);
	}

	/**
	 * 根据分页对象中的数据,对数据进行显示
	 * 
	 * @param page
	 * @return
	 */
	@Cacheable(value = "goods_ehcache")
	public void findGoodsByPage(Page page) {

		// 当前需要封装totalCount.totalPageCount,records,pageNum=1

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Goods.class);
		Map<String, String> map = page.getParameterMap();

		// 使用QBC方式查询数据,拼接sql语句;
		if (!StringUtils.isBlank(map.get("nm"))) {
			detachedCriteria.add((Restrictions.eq("nm", map.get("nm"))));
		}
		if (!StringUtils.isBlank(map.get("name"))) {
			detachedCriteria.add((Restrictions.like("name", map.get("name"))));
		}
		if (!StringUtils.isBlank(map.get("store.id"))) {
			// 注意这里的store是一个store对象,而不只是一个Id;
			Store store = new Store();
			store.setId(map.get("store.id"));
			
			System.out.println("storeid="+map.get("store.id"));
			
			detachedCriteria.add((Restrictions.eq("store", store)));
		}

		// 封装totalCount,
		//detachedCriteria.setProjection(Projections.count("name"));
		detachedCriteria.setProjection(Projections.rowCount());
		Long totalCount = goodsDAO.findTotalCount(detachedCriteria);
		page.setTotalCount(totalCount);
		
		//计算totalPageCount((总页数=总记录数+每页显示记录数-1)/每页显示记录数)
		//这里除数为1.0最好
		int totalPageCount=(int) (((totalCount+page.getNumberPerPage()-1)/page.getNumberPerPage()));
		page.setTotalPageCount(totalPageCount);
		
		//firstResult指第一个显示的记录角标,从0开始;maxResults指每页显示最大的记录数;
		int firstResult=(page.getPageNum()-1)*page.getNumberPerPage();
		int maxResults=page.getNumberPerPage();

		//这里会改变结果集的封装形式;所以再使用前需要进行清除还原;
		detachedCriteria.setProjection(null);
		
		// 封装records,需要显示的商品集合,
		List<Goods> goods=goodsDAO.findByDetachedCriteria(detachedCriteria,firstResult,maxResults);
		page.setRecords(goods);
		
		//到这里,page中所有的数据都存在了,其中有每页显示记录数和起始页码为默认值,可以在页面中传递参数对其进行修改;

	}

	/**
	 * 查询所有的货物单位信息
	 * @param unit
	 * @return
	 */
	public List<String> findUnitLike(String unit) {
		return goodsDAO.findUnitLike(unit);
	}

}
