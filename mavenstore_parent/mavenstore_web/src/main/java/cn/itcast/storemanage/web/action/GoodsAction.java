package cn.itcast.storemanage.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.storemanage.domain.Goods;
import cn.itcast.storemanage.domain.Userinfo;
import cn.itcast.storemanage.page.Page;
import cn.itcast.storemanage.service.GoodsService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

public class GoodsAction extends ActionSupport implements ModelDriven<Goods> {
	// t添加获取当前页码的模型驱动
	// 应该两种类型都是可以使用的,模型驱动的时候,会被自动进行转换,但只能针对基本类型数据进行自动转换;
	// 复杂数据需要定义转换器才能实现;
	// private String pageNum;
	private int pageNum;

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	// public void setPageNum(String pageNum) {
	// this.pageNum = pageNum;
	// }

	private Goods goods = new Goods();

	public Goods getModel() {
		return this.goods;
	}

	private GoodsService goodsService;

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	// 根据nm简记码,进行查询商品信息(ajax离焦查询)
	public String findGoodsByNm() {

		Goods goodsByFind = goodsService.findGoodsByNm(goods.getNm());

		// 将查询到的信息放入栈顶(返回json格式数据给对应的ajax请求)
		ActionContext.getContext().getValueStack().push(goodsByFind);

		// System.out.println(goodsByFind);

		return "findGoodsByNmSuccess";
	}

	/**
	 * 商品入库时的方法
	 */
	public String saveGoods() {
		Userinfo userinfo = (Userinfo) ServletActionContext.getRequest()
				.getSession().getAttribute("userinfo");
		if (userinfo == null) {
			// 说明当前处于无登陆状态,基本不可能,因为已经添加了session的登录状态验证拦截器了
			return "noLogin";
		}
		// userinfo肯定是存在的;这时候goods中保存的store只有一个storeid信息;
		goodsService.saveGoods(goods, userinfo.getName());

		// System.out.println(goods);

		return "saveGoodsSuccess";
	}

	/**
	 * 商品出库操作的方法(肯定是已经存在的商品了)
	 */
	@InputConfig(resultName = "outGoods_Input")
	public String outGoods() {

		// System.out.println(goods);

		Userinfo userinfo = (Userinfo) ServletActionContext.getRequest()
				.getSession().getAttribute("userinfo");

		if (userinfo == null) {
			// 说明当前处于无登陆状态,基本不可能,因为已经添加了session的登录状态验证拦截器了
			return "noLogin";
		}
		// userinfo肯定是存在的;这时候goods中保存的store只有一个id信息;

		// 判断货物是否充足;这个判断可以在action做,应该实现起来更方便;
		Goods findGoodsTotalNum = goodsService.findGoodsByNm(goods.getNm());
		if (findGoodsTotalNum.getAmount() < goods.getAmount()) {
			this.addFieldError("amount",
					"商品数量不足,当前共有商品:" + findGoodsTotalNum.getAmount());
			return "outGoods_Input";
		}

		goodsService.outGoods(goods, userinfo.getName());

		return "outGoodsSuccess";
	}

	/**
	 * 显示所有商品的方法
	 */
	public String listAllGoods() {
		List<Goods> goodses = goodsService.findAllGoods();

		// 放入值栈栈顶
		ActionContext.getContext().getValueStack().push(goodses);// 这样方便显示;(利用jqery取值方便)
		/**
		 * 这一个是不能省略的,否则struts2的标签无法显示;
		 */
		ActionContext.getContext().put("goodses", goodses);// 这样方便使用struts2标签显示;

		/**
		 * 下面添加了这里显示全部商品信息的方法;(当添加商品的时候,选择退出的时候,这里会执行,简便的查看所有商品的方法,不实用分页)
		 */
		Page<Goods> page = new Page<Goods>();
		// 封装page中的数据信息(也可以自己手动封装)
		// page.setParameterMap(null);
		// 必须封装需要显示的所有货物信息
		page.setRecords(goodses);

		// 设置默认的显示信息即可;
		// 设置当前页码和最大页码都为1;这两个参数,在page类中已经可以获取,这里可以省略;
		// page.setPageNum(1);
		// page.setTotalPageCount(1);
		// 每页显示商品记录数为最大值;因为只需要显示一页即可
		page.setNumberPerPage(goodses.size());
		page.setTotalCount(Long.parseLong(goodses.size() + ""));

		// // 放入值栈栈顶,因为page中包含了所有的信息,所以只需要page就行了;
		// 而行参数中,传递的是page的引用,所以不用返回值了;
		ActionContext.getContext().put("page", page);// 这样方便使用struts2标签显示;

		return "listAllGoods";
	}

	/**
	 * 显示所有商品的方法(ajax异步查询显示联想列表)
	 */
	public String listAllGoods_ajax() {
		List<Goods> goodses = goodsService.findAllGoods();

		// 放入值栈栈顶
		ActionContext.getContext().getValueStack().push(goodses);// 这样方便显示;

		return "listAllGoods_ajax";
	}

	/**
	 * 显示所有商品的名称方法(ajax异步查询显示联想列表) 方式一
	 */
	public String listAllGoodsNames_ajax1() {
		List<Goods> goodses = goodsService.findAllGoods();

		List<String> goodsesNames = new ArrayList<String>();
		for (Goods goods : goodses) {
			goodsesNames.add(goods.getName());
		}

		// 放入值栈栈顶
		ActionContext.getContext().getValueStack().push(goodsesNames);// 这样方便显示;

		return "listAllGoodsNames_ajax";
	}

	/**
	 * 显示所有商品的名称方法(ajax异步查询显示联想列表) 方式二,直接查询获取对应的名字数据的数组
	 */
	public String listAllGoodsNames_ajax2() {
		List<String> goodsesNames = goodsService.findAllGoodsNames();

		// System.out.println(Arrays.toString(goodsesNames.toArray()));

		// 放入值栈栈顶
		ActionContext.getContext().getValueStack().push(goodsesNames);// 这样方便显示;

		return "listAllGoodsNames_ajax";
	}

	/**
	 * 最重要的!!! 显示所有商品的名称方法(ajax异步查询显示联想列表的第二种方式) 方式三,直接查询获取对应的名字的对象(动态实时查询数据库)
	 */
	/**
	 * 暂时在货物管理主页面查询的位置上使用了这个方法;
	 * 
	 * @return
	 */
	public String listAllGoodsNames_ajax3() {

		// 因为传送的数据,需要进行中文的URL编码;
		String name = goods.getName();
		// try {
		// name = URLEncoder.encode(name,"utf-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }

		List<Goods> goodses = goodsService.findSearchedGoods(name);

		// 放入值栈栈顶
		ActionContext.getContext().getValueStack().push(goodses);// 这样方便显示;

		return "listAllGoodsNames_ajax";
	}

	/**
	 * 条件查询(nm,name和仓库,需要拼接sql语句,且使用like模式查询nm和name)
	 * 
	 * 方式失败!!!
	 */
	public String searchGoods0() {
		// 查看信息是否全部获取(nm,name和store.id)可以
		// System.out.println(goods);

		Map<String, String> map = new HashMap<String, String>();
		String sroreid = goods.getStore().getId();
		String nm = goods.getNm();
		String name = goods.getName();

		if (StringUtils.isNotBlank(nm)) {
			map.put("nm", nm);
		}
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		// 这个名称是不一致的;
		if (StringUtils.isNotBlank(sroreid)) {
			map.put("sroreid", sroreid);
		}

		List<Goods> goodses = goodsService.findGoodsByConditions(map);
		// // 放入值栈栈顶
		// ActionContext.getContext().getValueStack().push(goodses);//
		// 这样方便显示;(利用jqery取值方便)
		ActionContext.getContext().put("goodses", goodses);// 这样方便使用struts2标签显示;

		return "listSearchGoods";
	}

	/**
	 * 条件查询(nm,name和仓库,需要拼接sql语句,且使用like模式查询nm和name)
	 * 
	 * 方式二
	 */
	public String listSearchGoodsByPage() {

		Map<String, String> map = new HashMap<String, String>();

		String sroreid = null;
		String nm = null;
		String name = null;

		nm = goods.getNm();
		name = goods.getName();
		try {
			sroreid = goods.getStore().getId();
		} catch (Exception e) {
			sroreid = null;
		}

		if (StringUtils.isNotBlank(nm)) {
			map.put("nm", nm);
		}
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		// 这个名称是不一致的;
		if (StringUtils.isNotBlank(sroreid)) {
			map.put("store.id", sroreid);
		}
		Page<Goods> page = new Page<Goods>();
		// 封装page中的数据信息(也可以自己手动封装)
		page.setParameterMap(map);

		// 封装模型中的pageNum;
		if (pageNum != 0) {
			// System.out.println("执行了pageNum");
			page.setPageNum(pageNum);
		}
		// if (StringUtils.isNotBlank(pageNum)) {
		// page.setPageNum(Integer.parseInt(pageNum));
		// }

		// 根据分页对象中的数据,对数据进行显示
		goodsService.findGoodsByPage(page);

		// // 放入值栈栈顶,因为page中包含了所有的信息,所以只需要page就行了;
		// 而行参数中,传递的是page的引用,所以不用返回值了;
		ActionContext.getContext().put("page", page);// 这样方便使用struts2标签显示;

		// System.out.println(page.getRecords().size());
		// System.out.println(page.getPageNum());

		return "listAllGoods";
		// return "listSearchGoodsByPage";
	}

	/**
	 * 只是一个回显的方法,没有其他用途,可以更新时删除;
	 * 
	 * 这里可以获取数据后,保存到值栈,回显添加刚才点击的商品信息;
	 * @return
	 */
	public String showSave() {

//		try {
//			goods.setName(new String(goods.getName().getBytes("iso8859-1"),
//					"utf-8"));
//			goods.setUnit(new String(goods.getUnit().getBytes("iso8859-1"),
//					"utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}

		// 这里可以获取数据后,保存到值栈,回显添加刚才点击的商品信息;
		// 这些信息已经封装到了model中了;
		ActionContext.getContext().getValueStack().push(goods);

		 System.out.println(goods);
		
		return "showSave";
	}
	public String showOut() {
		
		// 这里可以获取数据后,保存到值栈,回显添加刚才点击的商品信息;
		// 这些信息已经封装到了model中了;
		ActionContext.getContext().getValueStack().push(goods);
		
		 System.out.println(goods);
		
		return "showOut";
	}
	public String showHis() {
		
		// 这里可以获取数据后,保存到值栈,回显添加刚才点击的商品信息;
		// 这些信息已经封装到了model中了;
		ActionContext.getContext().getValueStack().push(goods);
		
//		 System.out.println(goods);
		
		return "showHis";
	}

	/**
	 * 自动补全商品单位信息
	 * 
	 * @return
	 */
	public String findUnitLike() {

		List<String> unitLikeTips = goodsService.findUnitLike(goods.getUnit());

		// JSON格式数据返回,在result中不需要对应页面
		ActionContext.getContext().getValueStack().push(unitLikeTips);

		// System.out.println(unitLikeTips);

		return "findUnitLike_ajjax";
	}

}
