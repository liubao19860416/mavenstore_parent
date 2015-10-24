package cn.itcast.storemanage.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.storemanage.dao.StoreDAO;
import cn.itcast.storemanage.domain.Store;

public class StoreDAOImpl extends HibernateDaoSupport implements StoreDAO {

	/**
	 * 查找所有仓库信息
	 */
	public List<Store> findAll() {

		List<Store> stores = this.getHibernateTemplate().findByNamedQuery(
				"Store.findAll");

		return stores;
	}

	/**
	 * 添加仓库信息
	 */
	public void saveStore(Store store) {
		this.getHibernateTemplate().save(store);
	}

	/**
	 * 删除仓库信息
	 * 
	 * @param store
	 */
	public void delete(Store store) {
		this.getHibernateTemplate().delete(store);
	}
	/**
	 * 按照id查找仓库信息(一般是回显或者删除或者其他)
	 * @param id
	 * @return
	 */
	public Store findById(String id) {
		return this.getHibernateTemplate().get(Store.class, id);
	}
	/**
	 * 更新仓库信息
	 * @param store
	 */
	public void update(Store store) {
		this.getHibernateTemplate().update(store);
	}

}
