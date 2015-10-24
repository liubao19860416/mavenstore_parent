package cn.itcast.storemanage.service.impl;

import cn.itcast.storemanage.dao.UserinfoDAO;
import cn.itcast.storemanage.domain.Userinfo;
import cn.itcast.storemanage.service.UserinfoService;

public class UserinfoServiceImpl implements UserinfoService {

	private UserinfoDAO userinfoDAO;
	
	public void setUserinfoDAO(UserinfoDAO userinfoDAO) {
		this.userinfoDAO = userinfoDAO;
	}

	/**
	 * 登录方法
	 */
	public Userinfo login(Userinfo userinfo) {
		return userinfoDAO.login(userinfo);
	}

}
