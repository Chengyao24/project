package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Porder;

//@Repository //有繼承JpaRepository這個annotation可以不用
public interface BackPorderRepository extends JpaRepository<Porder, Long>{

	 //Custom query
	 @Query(value = "select * from porder where orderId like %:keyword% or status like %:keyword%", nativeQuery = true)
	 List<Porder> findByKeyword(@Param("keyword") String keyword);
	 
}