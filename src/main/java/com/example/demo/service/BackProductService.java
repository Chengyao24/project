package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Member;
import com.example.demo.model.Product;
import com.example.demo.repository.BackProductRepository;


public interface BackProductService {

	List <Product> getAllProducts();
    void saveProduct(Product product);
    Product getProductById(long id);
    void deleteProductById(long id);
    public List<Product> getByKeyword(String keyword);

}