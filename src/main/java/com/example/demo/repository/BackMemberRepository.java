package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

//@Repository //有繼承JpaRepository這個annotation可以不用
public interface BackMemberRepository extends JpaRepository<Member, Long>{

	 //Custom query
	 @Query(value = "select * from member where mobile like %:keyword% or name like %:keyword%", nativeQuery = true)
	 List<Member> findByKeyword(@Param("keyword") String keyword);
}
