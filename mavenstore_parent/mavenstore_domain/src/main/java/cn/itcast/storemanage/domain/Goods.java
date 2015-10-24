package cn.itcast.storemanage.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * Goods entity. @author MyEclipse Persistence Tools
 */

public class Goods implements java.io.Serializable {

	// Fields

	private String id;//id自动生成
	private Store store;//对应的仓库信息
	private String name;//名称
	private String nm;//简记码
	private String unit;//单位
	private Double amount;//数量
	private Set histories = new HashSet(0);//历史记录

	/**
	 * 为了显示ajax查询数据使用的;
	 * 和jsp中ajax请求出的focus等方法相对应
	 * @return
	 */
//	public String getLable(){
//		return this.name;
//	}
	
	public String getValue(){
		return this.name;
	}

	
	// Constructors

	@Override
	public String toString() {
		return "Goods [id=" + id + ", store=" + store + ", name=" + name
				+ ", nm=" + nm + ", unit=" + unit + ", amount=" + amount + "]";
	}

	/** default constructor */
	public Goods() {
	}

	/** minimal constructor */
	public Goods(String id) {
		this.id = id;
	}

	/** full constructor */
	public Goods(String id, Store store, String name, String nm, String unit,
			Double amount, Set histories) {
		this.id = id;
		this.store = store;
		this.name = name;
		this.nm = nm;
		this.unit = unit;
		this.amount = amount;
		this.histories = histories;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNm() {
		return this.nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@JSON(serialize = false)
	public Set getHistories() {
		return this.histories;
	}

	public void setHistories(Set histories) {
		this.histories = histories;
	}

}