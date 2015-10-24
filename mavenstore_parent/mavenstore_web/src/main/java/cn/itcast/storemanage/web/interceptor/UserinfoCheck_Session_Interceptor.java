package cn.itcast.storemanage.web.interceptor;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.itcast.storemanage.domain.Userinfo;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class UserinfoCheck_Session_Interceptor extends MethodFilterInterceptor {

	/**
	 * 该功能我定义在了commonPackage包中了;现在其他所有的包都继承了这个commonPackage包了
	 * 定义判断是否登录状态的拦截器,如果没有登录,则直接跳转到登陆界面! 
	 * 记录日志,添加了每一个action的运行时间的代码,方便优化效率;
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		String result = "noLogin";// 对应的就是一个默认处理结果
		HttpSession session = ServletActionContext.getRequest().getSession();
		Userinfo user = (Userinfo) session.getAttribute("userinfo");
		
		System.out.println("拦截器前执行了===");
		if (user == null) {
			// 如果用户没有登录，返回登录页面。
			System.out.println("用户登录失败!!!" );
			return result;
		}
		
		//这里是拦截前的位置
		result = invocation.invoke();
		//这里是拦截后的位置
		System.out.println("拦截器后执行了===");

		System.out.println("用户正常登录:" + user);
		return result;

	}
	
	@Override
	public void destroy() {
		System.out.println("拦截器2销毁了!!!");
		super.destroy();
	}

	@Override
	public void init() {
		System.out.println("拦截器2初始化了!!!");
		super.init();
	}
	
	
	
}
