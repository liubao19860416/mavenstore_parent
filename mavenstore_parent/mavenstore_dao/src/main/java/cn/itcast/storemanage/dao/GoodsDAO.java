package cn.itcast.storemanage.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.storemanage.domain.Goods;

public interface GoodsDAO {

	/**
	 * 根据简约码,查询商品信息,进行自动补全显示使用
	 * 
	 * @param nm
	 * @return
	 */
	Goods findByNm(String nm);

	/**
	 * 保存商品信息
	 * 
	 * @param goodsFindById
	 */
	void save(Goods goods);

	/**
	 * 根据id查找商品信息
	 * 
	 * @param id
	 * @return
	 */
	Goods findById(String id);

	/**
	 * 查找所有商品
	 * 
	 * @return
	 */
	List<Goods> findAll();

	/**
	 * 条件查询(nm,name和仓库,需要拼接sql语句,且使用like模式查询nm和name)
	 */
	List<Goods> findByConditions(Map<String, String> map);

	/**
	 * 显示所有商品的名称方法 方式二,直接查询获取对应的名字数据的数组
	 */
	List<String> findAllNames();

	/**
	 * 显示所有商品的名称方法(模糊查询)
	 */
	List<Goods> findSearchedGoods(String name);

	/**
	 * 查询总共记录条数的语句
	 * @param detachedCriteria 
	 * 
	 * @return
	 */
	Long findTotalCount(DetachedCriteria detachedCriteria);


	/**
	 * 使用QBC方式查询数据,拼接sql语句;
	 * @param detachedCriteria
	 * @param startIndex
	 * @param maxIndex
	 * @return
	 */
	List<Goods> findByDetachedCriteria(DetachedCriteria detachedCriteria,
			int firstResult, int maxResults);

	/**
	 * 查询所有的货物单位信息
	 * @param unit
	 * @return
	 */
	List<String> findUnitLike(String unit);

}
