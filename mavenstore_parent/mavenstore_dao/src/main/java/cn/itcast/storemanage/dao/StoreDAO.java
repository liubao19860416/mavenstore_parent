package cn.itcast.storemanage.dao;

import java.util.List;

import cn.itcast.storemanage.domain.Store;

public interface StoreDAO {

	/**
	 *查找所有仓库信息
	 * @return
	 */
	List<Store> findAll();

	/**
	 * 添加仓库
	 */
	void saveStore(Store store);

	/**
	 * 删除仓库信息
	 * @param store
	 */
	void delete(Store store);

	/**
	 * 按照id查找仓库信息(一般是回显或者删除或者其他)
	 * @param id
	 * @return
	 */
	Store findById(String id);
	/**
	 * 更新仓库信息
	 * @param store
	 */
	void update(Store store);

}
