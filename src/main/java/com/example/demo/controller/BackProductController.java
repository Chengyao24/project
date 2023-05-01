package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Admin;
import com.example.demo.model.Member;
import com.example.demo.model.Product;
import com.example.demo.repository.BackProductRepository;
import com.example.demo.service.BackAdminService;
import com.example.demo.service.BackMemberService;
import com.example.demo.service.BackProductService;
import com.example.demo.service.BackProductServiceImpl;
import com.example.demo.service.FilesStorageService;

@Controller
public class BackProductController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private FilesStorageService fs;
	
    @Autowired
    private BackAdminService adminService;
    
    public static String nowCode =BackController.userCode;
	
    @Autowired
    private BackProductService productService;
    
    // display list of employees
    @GetMapping("/productList")
    public String viewProductPage(Model model) {
    	Admin admins = adminService.getAdminCode(nowCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listProducts", productService.getAllProducts());
        return "Backproductlist";
    }

    @GetMapping("/showBackProductForm")
    public String showBackProductForm(Model model) {
        // create model attribute to bind form data
    	Product product = new Product();
        model.addAttribute("product", product);
        return "Backnew_product";
    }

    @PostMapping("/saveBackProduct")
    public String saveBackProduct(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
    	fs.save(file);
    	String filename = file.getOriginalFilename();
        String url = MvcUriComponentsBuilder.fromMethodName(BackProductController.class, "getImage", filename).build().toString();

        // 根據需求取得或創建 Product 對象
        // Product product = ...
        product.setImage(url);
        
        // save employee to database
    	productService.saveProduct(product);
    	
        return "BackproductList";
    }

    @GetMapping("/showBackProductForUpdate/{id}")
    public String showBackProductForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get employee from the service
    	Product product = productService.getProductById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("product", product);
        return "Backupdate_product";
    }

    @GetMapping("/deletebackproduct/{id}")
    public String deleteBackProduct(@PathVariable(value = "id") long id) {

        // call delete employee method 
        this.productService.deleteProductById(id);
        return "BackproductList";
    }
    
    @GetMapping("/searchBackProduct")
    public String searchProductBar(Product product, Model model, String keyword) {
     if(keyword!=null) {
      List<Product> plist = productService.getByKeyword(keyword);
      model.addAttribute("listProducts", plist);
     }else {
     List<Product> plist = productService.getAllProducts();
     model.addAttribute("listProducts", plist);}
     return "Backproductlist";
    }
    
    @GetMapping("/images/{filename:.+}")//有這個在新增時才回的去QQ
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
      Resource file = fs.load(filename);

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
