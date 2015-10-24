package cn.itcast.storemanage.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.storemanage.domain.History;

public interface HistoryDAO {

	/**
	 * 保存历史记录
	 * @param history
	 */
	void save(History history);

	/**
	 * 封装totalcount,查询总记录数;
	 * @param detachedCriteria
	 * @return
	 */
	Long findTotalCount(DetachedCriteria detachedCriteria);

	/**
	 * 根据page对象中的信息,查询符合条件的历史记录信息
	 * @param detachedCriteria
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	List<History> findHistoriesByPage(DetachedCriteria detachedCriteria,
			int firstResult, int maxResult);

	/**
	 * 查询所有的历史记录信息;
	 * @return
	 */
	List<History> findAllHistories();

}
