package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;
import com.example.demo.model.Porder;

//@Repository 下面有extends JpaRepository ,裡面有內建crud的功能可以不用寫
public interface PorderRepository extends JpaRepository<Porder, Long> {
	@Query("SELECT porder FROM Porder porder WHERE porder.member.memberId = :memberId")
	List<Porder> findPorderByMemberId(@Param("memberId") Long memberId);
}
