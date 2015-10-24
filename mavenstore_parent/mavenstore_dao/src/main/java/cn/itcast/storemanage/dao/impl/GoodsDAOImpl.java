package cn.itcast.storemanage.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.storemanage.dao.GoodsDAO;
import cn.itcast.storemanage.domain.Goods;

/**
 * 根据简约码,查询商品信息,进行自动补全显示使用
 * 
 * @param nm
 * @return
 */

@SuppressWarnings(value = "all")
public class GoodsDAOImpl extends HibernateDaoSupport implements GoodsDAO {

	public Goods findByNm(String nm) {
		List<Goods> list = this.getHibernateTemplate().findByNamedQuery(
				"goods.findByNm", nm);
		/**
		 * 这里需要注意下下,更加严谨一些
		 */
		return (list == null || list.size() == 0) ? null : list.get(0);
	}

	/**
	 * 保存商品信息
	 * 
	 * @param goodsFindById
	 */
	public void save(Goods goods) {
		this.getHibernateTemplate().save(goods);

	}

	/**
	 * 根据id查找商品信息
	 * 
	 * @param id
	 * @return
	 */
	public Goods findById(String id) {
		List<Goods> list = this.getHibernateTemplate().findByNamedQuery(
				"goods.findById", id);

		return (list == null || list.isEmpty() || list.size() == 0) ? null
				: list.get(0);
	}

	/**
	 * 查找所有商品
	 * 
	 * @return
	 */
	public List<Goods> findAll() {
		List list = this.getHibernateTemplate().findByNamedQuery(
				"goods.findAll");
		// 这里不需要进行判断
		return list;
	}

	/**
	 * 显示所有商品的名称方法 方式二,直接查询获取对应的名字数据的数组(这样对于回显数据不太好,所以使用第三种方式,便于回显数据)
	 */
	public List<String> findAllNames() {

		return this.getHibernateTemplate().findByNamedQuery(
				"goods.findAllNames");
	}

	/**
	 * 最重要的!!! 显示所有商品的名称方法(ajax异步查询显示联想列表的第二种方式) 方式三,直接查询获取对应的名字的对象(动态实时查询数据库)
	 */
	public List<Goods> findSearchedGoods(String name) {
		return this.getHibernateTemplate().findByNamedQuery(
		"goods.findSearchedGoods", "%" + name + "%");//%在hbm映射文件中不可以添加,所以只能写在这里;
	}

	/**
	 * 条件查询(nm,name和仓库,需要拼接sql语句,且使用like模式查询nm和name)
	 */
	// 方式一,不可以
	public List<Goods> findByConditions(Map<String, String> map) {
		String sql = "from Goods where 1=1 ";
		// map是保存选择条件的变量
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sql += " and " + entry.getKey() + " = '" + entry.getValue() + "'";
		}
		List<Goods> list = this.getHibernateTemplate().find(sql);

		return list;
	}

	/**
	 * 查询所有的记录总条数
	 */
	public Long findTotalCount(DetachedCriteria detachedCriteria) {
		
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		
		return  list==null?0l:list.get(0);
	}


	/**
	 * 按照条件进行查询
	 */
	public List<Goods> findByDetachedCriteria(DetachedCriteria detachedCriteria,
			int firstResult, int maxResults) {
		List list = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		return list;
	}

	/**
	 * 查询所有的货物单位信息
	 * @param unit
	 * @return
	 */
	public List<String> findUnitLike(String unit) {
		return this.getHibernateTemplate().findByNamedQuery("goods.findUnitLike");
	}



}
