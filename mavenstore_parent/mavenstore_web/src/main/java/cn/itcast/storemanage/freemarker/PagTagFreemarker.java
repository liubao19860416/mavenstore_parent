package cn.itcast.storemanage.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PagTagFreemarker extends SimpleTagSupport {
	private int pageNum; // 当前页码
	private long totalCount; // 总记录数
	private int numberPerPage; // 每页记录数
	private String paramUrl; // 查询条件参数url
	private String pageQueryUrl; // 服务器分页查询数据地址

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}

	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}

	public void setPageQueryUrl(String pageQueryUrl) {
		this.pageQueryUrl = pageQueryUrl;
	}

	@Override
	public void doTag() throws JspException, IOException {
		// 输出标签内容
		// 1、 获取 JspWriter对象
		JspWriter out = this.getJspContext().getOut();
		PageContext pageContext = (PageContext) this.getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		// 2、freemarker模板编程
		Configuration configuration = new Configuration();
		// 指定模板位置
		String directory = pageContext.getServletContext().getRealPath(
				"/WEB-INF/template");
		configuration.setDirectoryForTemplateLoading(new File(directory));
		// 获取模板对象
		Template template = configuration.getTemplate("pagTag.ftl",
				"utf-8");

		// 3、 准备数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("totalCount", totalCount); // 总记录数
		dataMap.put("numberPerPage", numberPerPage); // 每页记录数
		int totalPageCount = (int) ((totalCount + numberPerPage - 1) / numberPerPage);
		dataMap.put("totalPageCount", totalPageCount); // 总页数
		dataMap.put("pageNum", pageNum);
		dataMap.put("contextPath", request.getContextPath()); // web上下文 [项目名称]
		dataMap.put("pageQueryUrl", pageQueryUrl); // 请求路径
		dataMap.put("paramUrl", paramUrl); // 查询参数

		// 输出连续页码 当前页中心 前5 后5
		int begin = 1;
		int end = totalPageCount;
		if (pageNum - 5 > 0) {
			begin = pageNum - 5;
		}
		if (pageNum + 5 < totalPageCount) {
			end = pageNum + 5;
		}

		dataMap.put("begin", begin);
		dataMap.put("end", end);

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
