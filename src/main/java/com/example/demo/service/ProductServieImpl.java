package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Member;
import com.example.demo.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServieImpl implements ProductService {

	@Autowired
	private ProductRepository ProductRepository;

	@Override
	public List<Product> getAllProducts() {
		return ProductRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		Optional<Product> optional = ProductRepository.findById(id);
		Product product = null;
		if (optional.isPresent()) {
			product = optional.get();
		} else {
			throw new RuntimeException(" Product not found for id :: " + id);
		}
		return product;
	}

	@Override
	public void saveProduct(Product product) {
		this.ProductRepository.save(product);
	}

	@Override
	public void deleteProductById(Long id) {
		this.ProductRepository.deleteById(id);
	}

}
