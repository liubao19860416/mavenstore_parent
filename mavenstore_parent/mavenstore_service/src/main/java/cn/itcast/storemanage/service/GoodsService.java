package cn.itcast.storemanage.service;

import java.util.List;
import java.util.Map;

import cn.itcast.storemanage.domain.Goods;
import cn.itcast.storemanage.page.Page;

public interface GoodsService {

	/**
	 * 根据简记码,查询商品
	 * 
	 * @param nm
	 * @return
	 */
	Goods findGoodsByNm(String nm);

	/**
	 * 增加商品信息,需要判断该商品是否已经存在(更新或者添加),并添加历史记录信息;
	 * 
	 * @param goods
	 * @param name
	 */
	void saveGoods(Goods goods, String user);

	/**
	 * 查找所有商品
	 * @return
	 */
	List<Goods> findAllGoods();

	/**
	 * 商品出库操作的方法(肯定是已经存在的商品了)
	 */
	void outGoods(Goods goods, String name);

	/**
	 * 条件查询(nm,name和仓库,需要拼接sql语句,且使用like模式查询nm和name)
	 */
	List<Goods> findGoodsByConditions(Map<String, String> map);

	/**
	 * 显示所有商品的名称方法
	 * 方式二,直接查询获取对应的名字数据的数组
	 */
	List<String> findAllGoodsNames();

	/**
	 * 最重要的!!!
	 * 显示所有商品的名称方法(ajax异步查询显示联想列表的第二种方式)
	 * 方式三,直接查询获取对应的名字的对象(动态实时查询数据库)
	 */
	List<Goods> findSearchedGoods(String name);

	/**
	 * 根据分页对象中的数据,对数据进行显示
	 * @param page
	 * @return
	 */
	void findGoodsByPage(Page page);

	/**
	 * 查询所有的货物单位信息
	 * @param unit
	 * @return
	 */
	List<String> findUnitLike(String unit);

}
