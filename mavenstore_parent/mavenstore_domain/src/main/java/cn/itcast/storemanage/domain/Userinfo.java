package cn.itcast.storemanage.domain;

/**
 * Userinfo entity. @author MyEclipse Persistence Tools
 */

public class Userinfo implements java.io.Serializable {

	// Fields

	private String id;//用户id,自动生成
	private String name;//用户名,不用加密
	private String password;//密码,数据库存储的是md5加密的;

	
	// Constructors

	@Override
	public String toString() {
		return "Userinfo [id=" + id + ", name=" + name + ", password="
				+ password + "]";
	}

	/** default constructor */
	public Userinfo() {
	}

	/** minimal constructor */
	public Userinfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public Userinfo(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}