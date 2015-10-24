package cn.itcast.storemanage.service;

import cn.itcast.storemanage.domain.Userinfo;

public interface UserinfoService {

	/**
	 * 登录的方法
	 * @param userinfo
	 * @return
	 */
	Userinfo login(Userinfo userinfo);

}
