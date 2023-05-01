package com.example.demo.service;

import java.util.*;

import com.example.demo.model.*;

public interface ProductService {

	// 從數據庫中檢索所有產品
	public List<Product> getAllProducts();

	// 通過產品ID從數據庫中獲取產品
	public Product getProductById(Long id);

	public void saveProduct(Product product);

	public void deleteProductById(Long id);

}
