package com.example.demo.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Member;
import com.example.demo.model.Product;
import com.example.demo.repository.BackMemberRepository;

public interface BackMemberService {

	List <Member> getAllMembers();
    void saveMember(Member member);
    Member getMemberById(long id);
    void deleteMemberById(long id);
    public List<Member> getByKeyword(String keyword);
    // 取得分頁使用者資料
    //public Page<Member> getUsersPageable(int page);

}
