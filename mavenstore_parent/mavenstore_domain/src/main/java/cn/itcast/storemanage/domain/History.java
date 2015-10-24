package cn.itcast.storemanage.domain;

/**
 * History entity. @author MyEclipse Persistence Tools
 */

public class History implements java.io.Serializable {

	// Fields

	private String id;//历史id
	private Goods goods;//操作的商品信息
	private String datetime;//历史记录产生时间
	private String type;//0代表出库,1代表入库
	private Double amount;//当前入库或者出库商品数量
	private Double remain;//当前库中商品数量
	private String user;//操作人(当前session中的对对象即可)
	
	

	// Constructors

	@Override
	public String toString() {
		return "History [id=" + id + ", goods=" + goods + ", datetime="
				+ datetime + ", type=" + type + ", amount=" + amount
				+ ", remain=" + remain + ", user=" + user + "]";
	}

	/** default constructor */
	public History() {
	}

	/** minimal constructor */
	public History(String id) {
		this.id = id;
	}

	/** full constructor */
	public History(String id, Goods goods, String datetime, String type,
			Double amount, Double remain, String user) {
		this.id = id;
		this.goods = goods;
		this.datetime = datetime;
		this.type = type;
		this.amount = amount;
		this.remain = remain;
		this.user = user;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Goods getGoods() {
		return this.goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRemain() {
		return this.remain;
	}

	public void setRemain(Double remain) {
		this.remain = remain;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}