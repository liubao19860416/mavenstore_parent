package cn.itcast.storemanage.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.itcast.storemanage.domain.Store;
import cn.itcast.storemanage.service.StoreService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import flexjson.JSONSerializer;

public class StoreAction extends ActionSupport implements ModelDriven<Store> {
	// 配置模型驱动
	private Store store = new Store();

	public Store getModel() {
		return store;
	}

	private StoreService storeService;

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	
	//添加日志记录信息
	Logger logger=Logger.getLogger(StoreAction.class);
	
	// 显示所有仓库信息
	public String listAll() {

		List<Store> stores = storeService.findAllStores();

		ActionContext.getContext().put("stores", stores);

		// System.out.println(stores);
		logger.info("客户查询了所有商品信息:"+stores);

		return "listAll";
	}

	// 添加仓库信息(注解驱动,很重要的语句)
	@InputConfig(resultName="addCheck_Input")
	public String addStore() {

		storeService.addStore(store);
		
		logger.info("客户添加了商品信息:"+store);
		
		return "to_ListAllAction";
	}

	// 删除仓库信息
	public String deleteStore() {

		storeService.deleteStore(store);
		
		logger.info("客户删除了商品信息:"+store);

		return "to_ListAllAction";
	}

	// 修改仓库信息
	public String editStore() {

		Store findStore = storeService.findStoreById(store.getId());

		// 回显数据时使用
		ActionContext.getContext().getValueStack().push(findStore);
		//input默认为edit.jsp页面;(校验不通过也会停留在这个页面;)
		
		logger.info("客户正在修改商品信息:"+store);
		
		return Action.INPUT;
		// return "toEditStore";
	}

	// 更新仓库信息
	public String updateStore() {
		
		//System.out.println(store);
		logger.info("客户修改了商品信息:"+store);

		storeService.updateStore(store);
		
		return "to_ListAllAction";
	}
	
	//查询仓库列表,返回给alax请求,显示仓库下拉列表信息
	//方式一:Json方式
	public String ajaxList_1() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		//获取所有仓库信息
		List<Store> stores = storeService.findAllStores();
		
		JSONSerializer jsonSerializer = new JSONSerializer();
		//生成字符串json
		String serialize = jsonSerializer.exclude("*.class")
				.serialize(stores);
		
		//ActionContext.getContext().getValueStack().push(serialize);
		
		response.getWriter().write(serialize);
		
		return Action.NONE;
	}
	
	//方式二:使用struts2的Json插件方式
	public String ajaxList(){
		//获取所有仓库信息
		List<Store> stores = storeService.findAllStores();
		
		//将数据放到对象栈栈顶
		ActionContext.getContext().getValueStack().push(stores);
		
//		System.out.println(stores);
		
		return "ajaxlistSUCCESS";
	}
	

}
