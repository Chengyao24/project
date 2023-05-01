package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

//@Repository 下面有extends JpaRepository ,裡面有內建crud的功能可以不用寫
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	  Optional<Member> findByMobile(String mobile); //確認merber類別裡面有沒有引數mobile的這個物件
	  
	  Optional<Member> findByEmail(String email);

}
