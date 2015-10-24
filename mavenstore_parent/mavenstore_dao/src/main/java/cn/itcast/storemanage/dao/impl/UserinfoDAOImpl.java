package cn.itcast.storemanage.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.storemanage.dao.UserinfoDAO;
import cn.itcast.storemanage.domain.Userinfo;
import cn.itcast.storemanage.utils.MD5Utils;

@SuppressWarnings("all")
public class UserinfoDAOImpl extends HibernateDaoSupport implements UserinfoDAO{

	public Userinfo login(Userinfo userinfo) {
		
		List<Userinfo> list = this.getHibernateTemplate().findByNamedQuery("User.login",userinfo.getName(),MD5Utils.md5(userinfo.getPassword()));
		
		/**
		 * 这个语句很好!
		 */
		return list.size()==0?null:(Userinfo)(list.get(0));
//		return list.isEmpty()?null:(Userinfo)(list.get(0));
	}

}
