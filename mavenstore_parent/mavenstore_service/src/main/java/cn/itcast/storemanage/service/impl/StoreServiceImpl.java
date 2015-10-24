package cn.itcast.storemanage.service.impl;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;



import cn.itcast.storemanage.dao.StoreDAO;
import cn.itcast.storemanage.domain.Store;
import cn.itcast.storemanage.service.StoreService;

public class StoreServiceImpl implements StoreService {

	// 自动注入StoreDAO
	private StoreDAO storeDAO;

	public void setStoreDAO(StoreDAO storeDAO) {
		this.storeDAO = storeDAO;
	}

	/**
	 * 查询所有的仓库信息
	 */
	@Cacheable(value = "store_ehcache")
	public List<Store> findAllStores() {
//		return storeDAO.findAll();
		//或者(针对懒加载初始化,下面的代码,可以替代opensessioninview的效果)
		List<Store> list = storeDAO.findAll();
		for (Store store : list) {
			Hibernate.initialize(store.getGoodses());
		}
		return list;
	}

	/**
	 * 添加仓库
	 */
	@CacheEvict(value = "store_ehcache", allEntries = true)
	public void addStore(Store store) {
		storeDAO.saveStore(store);
	}

	/**
	 * 删除仓库信息
	 * 
	 * @param store
	 */
	@CacheEvict(value = "store_ehcache", allEntries = true)
	public void deleteStore(Store store) {
		/**
		 * 自定义异常抛出,是显示的信息更方便阅读
		 */
		if(store.getGoodses()!=null){
			throw new RuntimeException("仓库不为空,不能被删除");
		}
		storeDAO.delete(store);
	}

	/**
	 * 按照id查找仓库信息(一般是回显或者删除或者其他)
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(value = "store_ehcache")
	public Store findStoreById(String id) {
		return storeDAO.findById(id);
	}

	/**
	 * 更新仓库信息
	 * 
	 * @param store
	 */
	@CacheEvict(value = "store_ehcache", allEntries = true)
	public void updateStore(Store store) {
		storeDAO.update(store);
	}

}
