package com.example.demo.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.demo.model.Member;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public String showProducts(Model model, HttpServletRequest request) {

		List<Product> products = productService.getAllProducts();

//	    Product p1 = new Product(1, "TEA-1", "images/jia-ye-waTzoTvrFFs-unsplash.jpg", 10);
//		Product p2 = new Product(2, "TEA-2", "images/crystalweed-cannabis--tJ3tTNLNg8-unsplash.jpg", 20);
//	    Product p3 = new Product(3, "TEA-3", "images/crystalweed-cannabis-o_4I7fZ25fc-unsplash.jpg", 30);
//		Product p4 = new Product(4, "TEA-4", "images/New-Orleans-Style-Hover-M2.jpg", 40);
//		Product p5 = new Product(5, "TEA-5", "images/Honduras-Santa-Elena-Catracha-Community-Hover.jpg", 50);
//		Product p6 = new Product(6, "TEA-6", "images/wesual-click-sEGWnVTmLuk-unsplash.jpg", 60);
//
//		products.add(p1);
//		products.add(p2);
//		products.add(p3);
//		products.add(p4);
//		products.add(p5);
//		products.add(p6);

		HttpSession session = request.getSession();

		if (session.getAttribute("member") != null) {
			// 如果已經登入，添加一些額外的內容到產品清單中
			Member member = (Member) session.getAttribute("member");
			model.addAttribute("memberPhone", member.getMobile());
			model.addAttribute("memberPassword", member.getPassword());
		}

		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/preorder")
	public String showPreOrderView(Model model, HttpServletRequest request) {
		List<Product> products = productService.getAllProducts();

		HttpSession session = request.getSession();

		if (session.getAttribute("member") != null) {
			// 如果已經登入，添加一些額外的內容到產品清單中
			Member member = (Member) session.getAttribute("member");
			model.addAttribute("memberPhone", member.getMobile());
			model.addAttribute("memberPassword", member.getPassword());
		}

		model.addAttribute("products", products);
		return "preorder";
	}

	// 顯示商店 //訂購網頁
	@GetMapping("/shop")
	public String shop(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "shop";// 到時候在看購物網站檔案名叫甚麼
	}

	// 顯示後台產品管理頁
	@GetMapping("/maShop")
	public String mashop(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "maShop";// 到時候在看購物網站檔案名叫甚麼
	}

	// 新增產品輸入頁面
	@GetMapping("/showForm")
	public String showNewProductForm(Model model) {
		// create model attribute to bind form data
		Product product = new Product();
		model.addAttribute("product", product);
		return "new_product";
	}

	// 新增商品
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product) {
		// save product to database
		productService.saveProduct(product);
		return "redirect:/maShop";
	}

	// 修改商品
	@GetMapping("/showProductFormForUpdate/{id}")
	public String showProductFormForUpdate(@PathVariable Long id, Model model) {
		// get product from the service
		Product product = productService.getProductById(id);
		// set product as a model attribute to pre-populate the form
		model.addAttribute("product", product);
		return "update_product";
	}

	// 刪除商品-->突然覺得頁面不對www
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Long id) {

		// call delete employee method
		this.productService.deleteProductById(id);
		return "redirect:/maShop";
	}
}