package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Member;
import com.example.demo.service.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository MemberRepository;

    @Override
    public List <Member> getAllMember() {
        return MemberRepository.findAll();
    }

    @Override
    public void saveMember(Member member) {
    	
        this.MemberRepository.save(member);
    }

    @Override
    public Member getMemberByMemberId(long id) {
        Optional < Member > optional = MemberRepository.findById(id);
        Member member = null;
        if (optional.isPresent()) {
        	member = optional.get();
        } else {
            throw new RuntimeException(" Member not found for id :: " + id);
        }
        return member;
    }

    @Override
    public void deleteMemberById(long id) {
        this.MemberRepository.deleteById(id);
    }

    @Override
    public boolean existsByMobile(String mobile) {
        Optional<Member> optional = MemberRepository.findByMobile(mobile);
        return optional.isPresent();
    }

    @Override
    public boolean checkAccountAndPassword(String mobile, String password) {
        Optional<Member> optional = MemberRepository.findByMobile(mobile);
        if (optional.isPresent() && optional.get().getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

	@Override
	public Member getMemberByMobile(String mobile) {
		Optional<Member> optional=MemberRepository.findByMobile(mobile); //確認mobile這個引數在Member裡面有沒有資料
		
		return optional.get();
	}
	@Override
	public Member getMemberByEmail(String Email) {
		Optional<Member> optional=MemberRepository.findByEmail(Email);
		Member member=null; //要初始化
		if(optional.isPresent()) {
			member=optional.get();
			
		}
		return member;
	}
  

}

