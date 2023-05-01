package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 當一筆新資料被插入資料庫時，資料庫會自動生成一個唯一的主鍵值(pk)。
	@Column(name = "memberId")
	private Long memberId;

	private String mobile;

	private String password;

	private String name;

	private String email;
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Porder> porder = new ArrayList<>();

	public Member() {
	}

	public Member(Long memberId, String mobile, String password, String name, String email, List<Porder> porder) {
		super();
		this.memberId = memberId;
		this.mobile = mobile;
		this.password = password;
		this.name = name;
		this.email = email;
		this.porder = porder;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Porder> getPorder() {
		return porder;
	}

	public void setPorder(List<Porder> porder) {
		this.porder = porder;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + memberId + ", mobile=" + mobile + ", password=" + password + ", name=" + name
				+ ", email=" + email + "]";
	}

}
