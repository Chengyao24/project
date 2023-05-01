package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Member;

public interface MemberService {
	List<Member> getAllMember();

	void saveMember(Member member);

	Member getMemberByMemberId(long id);

	Member getMemberByMobile(String mobile);// 用來取得代入手機(帳號)的資料
	 Member getMemberByEmail(String email);


	void deleteMemberById(long id);

	boolean existsByMobile(String mobile);

	boolean checkAccountAndPassword(String mobile, String password);
}
