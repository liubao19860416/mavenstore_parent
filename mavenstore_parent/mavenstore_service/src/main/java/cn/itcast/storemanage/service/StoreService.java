package cn.itcast.storemanage.service;

import java.util.List;

import cn.itcast.storemanage.domain.Store;

public interface StoreService {

	/**
	 * 查询所有的仓库信息
	 * 
	 * @return
	 */
	List<Store> findAllStores();

	/**
	 * 添加仓库
	 * 
	 * @param store
	 */
	void addStore(Store store);

	/**
	 * 删除仓库信息
	 * @param store
	 */
	void deleteStore(Store store);

	/**
	 * 按照id查找仓库信息(一般是回显或者删除或者其他)
	 * @param id
	 * @return
	 */
	Store findStoreById(String id);

	/**
	 * 更新仓库信息
	 * @param store
	 */
	void updateStore(Store store);

}
