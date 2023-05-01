package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.model.Admin;
import com.example.demo.model.Member;
import com.example.demo.model.Product;
import com.example.demo.repository.BackAdminRepository;
import com.example.demo.service.BackAdminService;
import com.example.demo.service.BackMemberService;
import com.example.demo.service.BackPorderService;
import com.example.demo.service.BackProductService;


@Controller
public class BackController {
	
	@Autowired
	private HttpSession session;
	
    @Autowired
    private BackAdminService adminService;
    
    @Autowired
    private BackPorderService porderService;
    
    @Autowired
    private BackMemberService memberService; 
    
    @Autowired
    private BackProductService productService;

	public static String userCode;
		
    @GetMapping("/adminlogin")
	public ModelAndView Login() {
		ModelAndView model = new ModelAndView("Backadminlogin");
		return model;
	}
    
	@PostMapping("/adminlogin")
    public String dologin(@RequestParam(value = "code") String code, @RequestParam(value = "psw") String password, Model model) {
	
		userCode=code;
		// 從資料庫搜尋使用者
    	boolean isValid = adminService.checkCodeAndPassword(code, password);
    	 if (isValid) {

    		 Admin admins = adminService.getAdminCode(code);
    		 session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)         
             model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料

             
             model.addAttribute("listPorders", porderService.getAllPorders());
             return "Backporderlist";
             
         } else {
             // 登入失敗，顯示錯誤訊息
             //ModelAndView model = new ModelAndView("adminlogin");
             //model.addObject("errorMessage", "密碼錯誤！");
        	 
        	 model.addAttribute("errorMessage", "密碼錯誤！");
        	 return "Backadminlogin";
         }
    	    	 
     }
	
    @GetMapping("/backporder")
    public String viewPorderPage(Model model) {
		Admin admins = adminService.getAdminCode(userCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listPorders", porderService.getAllPorders());
        return "Backporderlist";
    }

	// display list of employees
    @GetMapping("/backmember")
    public String viewMemberPage(Model model) {
		Admin admins = adminService.getAdminCode(userCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listMembers", memberService.getAllMembers());
        return "Backmemberlist";
    }
    
 
    @GetMapping("/backproduct")
    public String viewProductPage(Model model) {
		Admin admins = adminService.getAdminCode(userCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listProducts", productService.getAllProducts());
        return "Backproductlist";
    }

    
    @GetMapping("/backadmin")
    public String viewAdminPage(Model model) {
		Admin admins = adminService.getAdminCode(userCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listAdmins", adminService.getAllAdmins());
        return "Backadminlist";
    }
}
