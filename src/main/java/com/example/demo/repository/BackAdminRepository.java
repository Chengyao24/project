package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Admin;
import com.example.demo.model.Member;

//@Repository //有繼承JpaRepository這個annotation可以不用
public interface BackAdminRepository extends JpaRepository<Admin, Long>{

	Optional<Admin> findByCode(String code);
	 //Custom query
	 @Query(value = "select * from admin where name like %:keyword% or role like %:keyword%", nativeQuery = true)
	 List<Admin> findByKeyword(@Param("keyword") String keyword);

}

