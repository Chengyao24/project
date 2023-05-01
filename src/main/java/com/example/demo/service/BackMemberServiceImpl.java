package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Member;
import com.example.demo.repository.BackMemberRepository;

@Service
public class BackMemberServiceImpl implements BackMemberService {

    @Autowired
    private BackMemberRepository memberRepository;

	//@Override
	//public Page<Member> getUsersPageable(int page) {
    //    Pageable pageable = PageRequest.of(page, 5); // 每頁顯示 5 筆資料
    //    return memberRepository.findAll(pageable);
	//}
    
    @Override
    public List <Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void saveMember(Member member) {
        this.memberRepository.save(member);
    }

    @Override
    public Member getMemberById(long id) {
    	//Optional是Lumda語法
        Optional <Member> optional = memberRepository.findById(id);
        Member member = null;
        if (optional.isPresent()) { //如果optional的指定id存在
            member = optional.get(); //optional取得資料
        } else {
            throw new RuntimeException(" Member not found for id :: " + id); //錯誤顯示:找不到指定id
        }
        return member;
    }

    @Override
    public void deleteMemberById(long id) {
        this.memberRepository.deleteById(id);
    }

	@Override
	public List<Member> getByKeyword(String keyword) {
		return memberRepository.findByKeyword(keyword);
	}
}

