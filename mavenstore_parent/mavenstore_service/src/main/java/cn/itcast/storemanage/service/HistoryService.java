package cn.itcast.storemanage.service;

import java.util.List;

import cn.itcast.storemanage.domain.History;
import cn.itcast.storemanage.page.Page;

public interface HistoryService {

	/**
	 * 根据Page对象,进行条件查询;
	 * @param page
	 * @return
	 */
	void findAllHistoriesByPage(Page<History> page);

	/**
	 * 查询所有的历史记录信息;
	 * @return
	 */
	List<History> findAllHistories();

}
