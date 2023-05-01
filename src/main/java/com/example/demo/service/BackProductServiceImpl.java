package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.BackProductRepository;

@Service
public class BackProductServiceImpl implements BackProductService {

    @Autowired
    private BackProductRepository productRepository;
    
    @Override
    public List <Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Product getProductById(long id) {
    	//Optional是Lumda語法
        Optional <Product> optional = productRepository.findById(id);
        Product product = null;
        if (optional.isPresent()) { //如果optional的指定id存在
        	product = optional.get(); //optional取得資料
        } else {
            throw new RuntimeException(" Product not found for id :: " + id); //錯誤顯示:找不到指定id
        }
        return product;
    }

    @Override
    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

	@Override
	public List<Product> getByKeyword(String keyword) {
		return productRepository.findByKeyword(keyword);
	}
}
