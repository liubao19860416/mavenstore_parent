package cn.itcast.storemanage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.storemanage.dao.HistoryDAO;
import cn.itcast.storemanage.domain.History;

public class HistoryDAOImpl extends HibernateDaoSupport implements HistoryDAO {

	/**
	 * 保存历史记录
	 * 
	 * @param history
	 */
	public void save(History history) {
		this.getHibernateTemplate().save(history);
	}

	/**
	 * 封装totalcount,查询总记录数;
	 * @param detachedCriteria
	 * @return
	 */
	public Long findTotalCount(DetachedCriteria detachedCriteria) {
		List list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		
		return (Long) (list==null?0l:list.get(0));
	}

	/**
	 * 根据page对象中的信息,查询符合条件的历史记录信息
	 * @param detachedCriteria
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<History> findHistoriesByPage(DetachedCriteria detachedCriteria,
			int firstResult, int maxResults) {
		
//		detachedCriteria.addOrder(Order.desc("datetime"));
		detachedCriteria.addOrder(Order.asc("datetime"));
		List list = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		
		return list;
	}

	/**
	 * 查询所有的历史记录信息;
	 * @return
	 */
	public List<History> findAllHistories() {
		List<History> list = this.getHibernateTemplate().findByNamedQuery("history.findAll");
		return list;
	}

	
	
}
