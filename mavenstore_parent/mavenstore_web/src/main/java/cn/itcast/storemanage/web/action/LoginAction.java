package cn.itcast.storemanage.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.itcast.storemanage.domain.Userinfo;
import cn.itcast.storemanage.service.UserinfoService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<Userinfo> {
	private Userinfo userinfo = new Userinfo();

	public Userinfo getModel() {
		return userinfo;
	}

	// 添加日志记录
	Logger logger = Logger.getLogger(LoginAction.class);

	// 属性注入
	private UserinfoService userinfoService;

	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}

	// 登录方法
	public String login() {

		// 添加的服务端校验方法(这里的userinfo肯定不会为空的,所以不需要检验为空了)
		if (userinfo.getName().isEmpty() || userinfo.getName().length() == 0
				|| userinfo.getName() == null) {
			this.addActionError("用户名不能为空");
			return Action.INPUT;
		}

		if (userinfo.getPassword().isEmpty() || userinfo.getPassword() == null
				|| userinfo.getPassword().length() == 0) {
			this.addActionError("密码不能为空");
			return Action.INPUT;
		}

		// userinfo存在用户名和密码后,在进行登录查询数据库
		Userinfo newUserinfo = userinfoService.login(userinfo);

		// System.out.println(newUserinfo);
		logger.info("当前登录用户为:{" + newUserinfo + "}");

		// 添加的服务端校验方法
		if (newUserinfo == null) {
			this.addActionError("用户名或者密码错误");
			return Action.INPUT;
		}

		// 登陆成功,将用户信息存放到session中;取出的时候使用ognl表达式取出来
		// ActionContext.getContext().getSession().put("userinfo", newUserinfo);
		ServletActionContext.getRequest().getSession()
				.setAttribute("userinfo", newUserinfo);

		return Action.SUCCESS;
	}

}
