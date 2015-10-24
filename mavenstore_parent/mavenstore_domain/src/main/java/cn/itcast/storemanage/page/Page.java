package cn.itcast.storemanage.page;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.itcast.storemanage.domain.Goods;

/**
 * 数据Bean，保存分页查询显示的一些信息
 * @param <T>
 * 
 */
@SuppressWarnings("all")
public class Page<T> {
	// 分页查询条件
	private int pageNum =1; // 页码（当前显示哪一页）

	private int numberPerPage = 5; // 每页记录数
	/**
	 * 应该要有一个pramaUrl,保顿的是当前需要访问的URL地址(或者需要提交的目的地址字符串信息,其中包含了我们需要的请求参数等信息)
	 * 在下面的更新的项目中实现; 并且将现在的map更换为从request中获取,增强其扩展性;
	 */
	// HTTP协议
	
	// private String pramaUrl;// key=value&key=value&key=value...
	// 服务器
	private Map<String, String> parameterMap; // 通过request获取

	// 结果显示数据
	private Long totalCount; // 总记录数（满足当前条件）,需要查询获取

	// 总页数(可以计算,可以在get方法中进行计算出来;)
	// =(totalCount+numberPerPage-1)/numberPerPage
	private int totalPageCount;

	/**
	 * 需要注意这里的List是通用的,所以这里不能使用具体的泛型;
	 */
	private List<T> records; // 当前页显示数据
	
	//这里可以移动到方法里面吗???
	/**
	 * 这个参数的额主要作用是在条件查询,换页的时候,提供显示数据的参数的方法;
	 * 也就是实现待条件的分页效果显示;
	 * 这个url里面封装的就是当前的查询条件;
	 */
	// key=value&key=value&key=value...
	public String getParamUrl() throws UnsupportedEncodingException {
		String paramUrl="";
		parameterMap=this.getParameterMap();
		for (Entry<String, String> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();
			String parameterValue = entry.getValue();
			
			// 手动对中文内容编码
			parameterValue = URLEncoder.encode(parameterValue, "utf-8");
			if (paramUrl.equals("")) {
				paramUrl += parameterName + "=" + parameterValue;
			} else {
				paramUrl += "&" + parameterName + "=" + parameterValue;
			}
		}
		//使用s:param传递参数字符串????;不可以实现;
		//System.out.println("Page中输出的获取的拼接的查询条件:"+paramUrl);
		
		return paramUrl;
	}
	

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumberPerPage() {
		return numberPerPage;
	}

	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}

	public Map getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map parameterMap) {
		this.parameterMap = parameterMap;
	}

	public Long getTotalCount() {
		if(totalCount==null){
			totalCount=0l;
		}
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	// 添加的自动获取
	public int getTotalPageCount() {
		if(totalCount==null){
			totalCount=0l;
		}
		this.totalPageCount = (int) ((getTotalCount() + getNumberPerPage() - 1) / getNumberPerPage());
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	@Override
	public String toString() {
		return "Page [pageNum=" + pageNum + ", numberPerPage=" + numberPerPage
				+ ", totalCount=" + totalCount + ", totalPageCount="
				+ totalPageCount + ", records=" + records + "]";
	}

}
