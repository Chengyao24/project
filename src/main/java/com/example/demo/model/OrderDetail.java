package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "orderDetail")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// 這裡不用設id,因為orderID欄位是透過一對多欄位新增過來的
	@ManyToOne
	@JoinColumn(name = "orderId", referencedColumnName = "orderId") // Foreign Key
	private Porder porder; // 用訂單

	@ManyToOne // 一個產品有多個明細orderID//orderID是要在結帳完後才會產生
	@JoinColumn(name = "productId", referencedColumnName = "productId")
	private Product product;
	private String productName;
	private Integer price;
	private Integer quantity;
	private Integer total;
	private Integer test;

	public OrderDetail() {
	}

	public OrderDetail(String productName, Integer quantity) {
		super();
		this.productName = productName;
		this.quantity = quantity;
	}

	public OrderDetail(Long id, Porder porder, Product product, String productName, Integer price, Integer quantity,
			Integer total) {
		super();
		this.id = id;
		this.porder = porder;
		this.product = product;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Porder getPorder() {

		if (porder == null) {
			porder = new Porder();

		}

		return porder;
	}

	public void setPorder(Porder porder) {
		this.porder = porder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "OrderDetail [" + "productName=" + productName
				+ ", price=" + price + ", quantity=" + quantity + ", total=" + total + "]";
	}

	public Integer getTest() {
		return test;
	}

	public void setTest(Integer test) {
		this.test = test;
	}
}