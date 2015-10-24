package cn.itcast.storemanage.web.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.storemanage.domain.History;
import cn.itcast.storemanage.page.Page;
import cn.itcast.storemanage.service.HistoryService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class HistoryAction extends ActionSupport implements
		ModelDriven<History> {
	// 模型驱动
	private History history = new History();

	public History getModel() {
		return history;
	}

	// 属性驱动
	private int pageNum;

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	// 属性注入
	private HistoryService historyService;

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public String listAllHsitoriesByPage() {

		// 获取查询条件,在Page对象中保存;
		Page<History> page = new Page<History>();

		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		Map<String, String[]> parameterMap = ServletActionContext.getRequest().getParameterMap();

		// 首先封装map数据到page对象中;(他们的泛型不一样,所以需要手动进行封装或者转换)
		// 这次采用第二种方式,从request中获取查询参数,而没有使用model对象获取;

		/**
		 * 需要定义一个方法,将该map<String,String[]>转换为page中的map<String,String>类型;
		 */
		Map<String,String>  map =new HashMap<String, String>();
		map=transferMap(parameterMap);
		page.setParameterMap(map);
		
		// System.out.println(page);

		// 设置起始显示页码(如果页面没有传递,默认从1开始),这里通过request获取;
		 if(pageNum!=0){
		 page.setPageNum(pageNum);
		 
		 //System.out.println("action中的pageNum:"+pageNum);
		 }

		// 查询所有的历史记录信息;
		historyService.findAllHistoriesByPage(page);

		// 还需要获取totalCount和records对象;通过service对象获取;

		ActionContext.getContext().put("page", page);

		// 测试代码
		// ServletActionContext.getRequest().getSession().removeAttribute("userinfo");

		return "listAllHsitoriesByPage";
	}

	public String listAllHsitories() {

		// 获取查询条件,在Page对象中保存;
		Page<History> page = new Page<History>();

		page.setParameterMap(null);

		// 设置起始显示页码(如果页面没有传递,默认从1开始)
		// if(pageNum!=0){
		// page.setPageNum(pageNum);
		// }

		// 查询所有的历史记录信息;
		List<History> histories = historyService.findAllHistories();

		page.setRecords(histories);
		page.setNumberPerPage(histories.size());
		page.setTotalCount(Long.parseLong(histories.size() + ""));

		// 还需要获取totalCount和records对象;通过service对象获取;

		ActionContext.getContext().put("page", page);

		return "listAllHsitories";
	}

	/**
	 * 转换map类型的方法 将该map<String,String[]>转换为page中的map<String,String>类型;
	 */
	private Map<String, String> transferMap(Map<String, String[]> parameterMap) {
		Map<String, String> map = new HashMap();
		if (parameterMap != null) {
			String[] goods_nm = (String[]) parameterMap.get("goods.nm");
			if (goods_nm != null && goods_nm.length > 0
					&& StringUtils.isNotBlank(goods_nm[0])) {
				map.put("goods.nm", goods_nm[0]);
			}
			String[] goods_name = (String[]) parameterMap.get("goods.name");
			if (goods_name != null && goods_name.length > 0
					&& StringUtils.isNotBlank(goods_name[0])) {
//				try {
//					//goods_name[0]=new String (goods_name[0].getBytes("iso8859-1"),"utf-8");
//					//goods_name[0] = URLEncoder.encode(goods_name[0], "utf-8");
//					//goods_name[0] = URLDecoder.decode(goods_name[0], "utf-8");
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				map.put("goods.name", goods_name[0].trim());
			}
			String[] goods_store_id = (String[]) parameterMap
					.get("goods.store.id");
			if (goods_store_id != null && goods_store_id.length > 0
					&& StringUtils.isNotBlank(goods_store_id[0].trim())) {
				// Store store = new Store();
				// store.setId(goods_store_id[0].trim());
				map.put("goods.store.id", goods_store_id[0].trim());
			}
//			String[] pageNum = (String[]) parameterMap.get("pageNum");
//			if (pageNum != null && pageNum.length > 0
//					&& StringUtils.isNotBlank(pageNum[0])) {
//				map.put("pageNum", pageNum[0].trim());
//			}

		}
		return map;

	}
}
