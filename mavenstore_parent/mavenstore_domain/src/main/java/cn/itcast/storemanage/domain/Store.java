package cn.itcast.storemanage.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * Store entity. @author MyEclipse Persistence Tools
 */

public class Store implements java.io.Serializable {

	// Fields

	private String id;//仓库id
	private String name;//仓库名称
	private String addr;//仓库地址
	private String manager;//仓库管理员
	private Set goodses = new HashSet(0);//仓库商品

	
	
	// Constructors

	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", addr=" + addr
				+ ", manager=" + manager + "]";
	}

	/** default constructor */
	public Store() {
	}

	/** minimal constructor */
	public Store(String id) {
		this.id = id;
	}

	/** full constructor */
	public Store(String id, String name, String addr, String manager,
			Set goodses) {
		this.id = id;
		this.name = name;
		this.addr = addr;
		this.manager = manager;
		this.goodses = goodses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	@JSON(serialize = false)
	public Set getGoodses() {
		return this.goodses;
	}

	
	public void setGoodses(Set goodses) {
		this.goodses = goodses;
	}

}