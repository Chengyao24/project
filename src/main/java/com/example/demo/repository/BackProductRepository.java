package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

//@Repository //有繼承JpaRepository這個annotation可以不用
public interface BackProductRepository extends JpaRepository<Product, Long>{
	//Custom query
	@Query(value = "select * from product where productId like %:keyword% or pname like %:keyword%", nativeQuery = true)
	List<Product> findByKeyword(@Param("keyword") String keyword);
}

