package cn.itcast.storemanage.dao;

import cn.itcast.storemanage.domain.Userinfo;

public interface UserinfoDAO {

	/**
	 * 登录方法
	 * @param userinfo
	 * @return
	 */
	Userinfo login(Userinfo userinfo);

}
