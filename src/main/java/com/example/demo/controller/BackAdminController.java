package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Admin;
import com.example.demo.model.Member;
import com.example.demo.repository.BackAdminRepository;
import com.example.demo.service.BackAdminService;
import com.example.demo.service.BackMemberService;

@Controller
public class BackAdminController {
	@Autowired
	private HttpSession session;

	@Autowired
	private BackAdminService adminService;
	  
    public static String nowCode =BackController.userCode;
	
	List<Admin> admins;

    // display list of employees
    @GetMapping("/adminList")
    public String viewAdminPage(Model model) {  
		Admin admins = adminService.getAdminCode(nowCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listAdmins", adminService.getAllAdmins());
        return "Backadminlist";
    }

	@GetMapping("/showAdminForm")
	public String showNewAdminForm(Model model) {
		// create model attribute to bind form data
		Admin admin = new Admin();
		model.addAttribute("admin", admin);
		return "Backnew_admin";
	}

	@PostMapping("/saveAdmin")
	public String saveAdmin(@ModelAttribute("admin") Admin admin) {
		// save employee to database
		adminService.saveAdmin(admin);
		return "Backadminlist";
	}

	@GetMapping("/showAdminForUpdate/{id}")
	public String showAdminForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get employee from the service
		Admin admin = adminService.getAdminById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("admin", admin);
		return "Backupdate_admin";
	}

	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable(value = "id") long id) {

		// call delete employee method
		this.adminService.deleteAdminById(id);
		return "Backadminlist";
	}
	
    @GetMapping("/searchAdmin")
    public String searchMemberBar(Admin admin, Model model, String keyword) {
    	    
     if(keyword!=null) {
      List<Admin> alist = adminService.getByKeyword(keyword);
      model.addAttribute("listAdmins", alist);
     }else {
     List<Admin> alist = adminService.getAllAdmins();
     model.addAttribute("listAdmins", alist);}
     return "Backadminlist";
    }
    
}