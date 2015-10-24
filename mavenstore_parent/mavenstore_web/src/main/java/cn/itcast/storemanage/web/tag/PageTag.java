package cn.itcast.storemanage.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 分页工具条
 * 
 */
public class PageTag extends SimpleTagSupport {

	// 输出连续页码 当前页中心 前5 后5,这里用常量代替
	private static final int NUMBER = 2;

	private int pageNum; // 当前页码
	private long totalCount; // 总记录数
	private int numberPerPage; // 每页记录数
	private String paramUrl; // 查询条件参数url

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

	@Override
	public void doTag() throws JspException, IOException {
		// 输出标签内容
		// 1、 获取 JspWriter对象
		JspWriter out = this.getJspContext().getOut();
		PageContext pageContext = (PageContext) this.getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		// 2、生成div输出分页工具条
		out.write("<div align=\"right\">");
		// 输出页码、总记录、每页记录数、总页数信息
		out.write("<span>总记录数：" + totalCount + "条</span>");
		out.write("<span>每页记录数：" + numberPerPage + "条</span>");
		int totalPageCount = (int) ((totalCount + numberPerPage - 1) / numberPerPage);
		out.write("<span>总页数：" + totalPageCount + "页</span>");
		out.write("<span>当前第：" + pageNum + "页</span>");
		// 输出首页 、上一页
		if (pageNum == 1) {
			// 已经是第一页（首页）
			out.write("<a>首页</a><a>上一页</a>");
		} else {
			// 不是第一页
			out.write("<a href=\"" + request.getContextPath()
					+ "/historyAction_listAllHsitoriesByPage.action?pageNum=1&"
					+ paramUrl + "\">首页</a>");
			out.write("<a href=\"" + request.getContextPath()
					+ "/historyAction_listAllHsitoriesByPage.action?pageNum="
					+ (pageNum - 1) + "&" + paramUrl + "\">上一页</a>");
		}

		// 输出连续页码 当前页中心 前5 后5,这里用常量代替
		int begin = 1;
		int end = totalPageCount;

		// 让每页显示5个页码(最多)
		if (totalPageCount>=5) {
			//是为了让页码显示为5个或者最大值
			if (pageNum + NUMBER <= 5) {
					begin = 1;
					end = 5;
			} else {
				begin=pageNum - NUMBER;
				end = pageNum + NUMBER;
				/**
				 * 这里需要再做一个判断,对end和begin确保现实的页码也是5个;
				 */
				if(end>=totalPageCount){
					end = totalPageCount;
					begin = totalPageCount-4;
				}
			}
		}else{
			if (pageNum - NUMBER >= 1) {
				begin = pageNum - NUMBER;
			} else {
				begin = 1;
			}

			if (pageNum + NUMBER <= totalPageCount) {

				end = pageNum + NUMBER;
			} else {
				end = totalPageCount;
			}
		}
		
		for (int i = begin; i <= end; i++) {
			if (i == pageNum) {
				// 当前页不给链接
				out.write("<a>[ " + i + " ]</a>");
			} else {
				// 不是当前页
				out.write("<a href=\""
						+ request.getContextPath()
						+ "/historyAction_listAllHsitoriesByPage.action?pageNum="
						+ i + "&" + paramUrl + "\">[ " + i + "] </a>");
			}
		}

		
		// 下一页、尾页
		if (pageNum == totalPageCount) {
			// 已经是最后一页
			out.write("<a>下一页</a><a>尾页</a>");
		} else {
			// 不是最后一页
			out.write("<a href=\"" + request.getContextPath()
					+ "/historyAction_listAllHsitoriesByPage.action?pageNum="
					+ (pageNum + 1) + "&" + paramUrl + "\">下一页</a>");
			out.write("<a href=\"" + request.getContextPath()
					+ "/historyAction_listAllHsitoriesByPage.action?pageNum="
					+ totalPageCount + "&" + paramUrl + "\">尾页</a>");
		}
		
		
		// 直接跳转到第几页
		out.write("<input type=\"text\" size=\"2\" name=\"selectedPageNum2\"/>");
		//设置总页数和被选中的页码的隐藏域;
		out.write("<input type=\"hidden\" name=\"selectedPageNum1\" value=\""
				+ paramUrl + "\"/>");
		out.write("<input type=\"hidden\"  id=\"totalPageCount\" value=\""
				+ totalPageCount + "\"/>");
		out.write("<input type=\"button\" id=\"selectedPageNum1\" value=\"go\" size=\"2\" />");

		out.write("</div>");
		out.flush();
	}
}
