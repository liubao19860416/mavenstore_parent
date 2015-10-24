package cn.itcast.storemanage.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cn.itcast.storemanage.domain.Userinfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class UserLoginCheckTag extends SimpleTagSupport {

	private Userinfo userinfo;
	private String logininfo;
	
	public void setLogininfo(String logininfo) {
		this.logininfo = logininfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	// 自己选择覆写该方法即可;
	@Override
	public void doTag() throws JspException, IOException {
		// 输出标签内容
		// 1、 获取 JspWriter对象
		JspWriter out = this.getJspContext().getOut();
		PageContext pageContext = (PageContext) this.getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		response.setContentType("text/html;charset=utf-8");

		// 2、freemarker模板编程
		Configuration configuration = new Configuration();
		// 指定模板位置
		String directory = pageContext.getServletContext().getRealPath(
				"/WEB-INF/template");
		configuration.setDirectoryForTemplateLoading(new File(directory));
		// 获取模板对象
		Template template = configuration.getTemplate("userLoginCheck.ftl",
				"utf-8");

		// 获取session中的当前登录用户信息
		 userinfo = (Userinfo) request.getSession().getAttribute(
		 "userinfo");
		 
		// 3、 准备数据存储的map
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		 
		 if(userinfo==null){
			 userinfo = new Userinfo();
			 //设置默认用户名
			// userinfo.setName("liubao");
			  userinfo.setName("");
			 dataMap.put("logininfo", "<a>请先登录!!!</a>"); 
		 }

		// 在这里也可以对用户的权限进行控制
		List<Userinfo> userinfos = new ArrayList<Userinfo>();

		/**
		 * 这里需要获取用户的权限信息;
		 */

		if (userinfos.contains(userinfo)) {
			//System.out.println("具备权限");
		} else {
			//System.out.println("不具备权限");
		}

		// 3、 准备数据
//		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("userinfo", userinfo); // 总记录数

		// 4、 合并输出
		try {
			//template.process(dataMap, new PrintWriter(System.out)); // 输出到控制台
			template.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException("模板错误！");
		}

	}
}
