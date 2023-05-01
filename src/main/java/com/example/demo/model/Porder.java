package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "porder ")
public class Porder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	@ManyToOne // (targetEntity = Customer.class, fetch = FetchType.LAZY) //一般不需要顯式設置
	@JoinColumn(name = "memberId", referencedColumnName = "memberid") // Foreign Key
	private Member member;
	private String status;
	private Timestamp createDate;
	private Timestamp updateDate;
	private String sn;// 訂單編號
	private Integer type;
	private Double paymoney;

	@OneToMany(mappedBy = "porder", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetail = new ArrayList<>();

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getStatus() {
		if (status == null) {
			status = "";
		}

		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Porder() {
		super();

	}

	public Porder(Long orderId, Member member, String status, Timestamp createDate, Timestamp updateDate, String sn,
			List<OrderDetail> orderDetail) {
		super();
		this.orderId = orderId;
		this.member = member;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.sn = sn;
		this.orderDetail = orderDetail;
	}

	public void setMember(Long memberId) {

	}

	public void setOrderId(Porder porder) {

	}

	public Double getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
	}
	
	

}
