package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Admin;
import com.example.demo.model.Porder;
import com.example.demo.service.BackAdminService;
import com.example.demo.service.BackPorderService;
import com.example.demo.service.BackPorderServiceImpl;


@Controller
public class BackPorderController {
	
	@Autowired
	private HttpSession session;

    @Autowired
    private BackPorderService porderService;
    
    @Autowired
    private BackAdminService adminService;
    
    public static String nowCode =BackController.userCode;

    
    // display list of employees
    @GetMapping("/porderList")
    public String viewPorderPage(Model model) {
    	Admin admins = adminService.getAdminCode(nowCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listPorders", porderService.getAllPorders());
        return "Backporderlist";
    }

    @GetMapping("/showBackPorderForm")
    public String showNewPorderForm(Model model) {  
        // create model attribute to bind form data
    	Porder porder = new Porder();
        model.addAttribute("porder", porder);
        return "Backnew_porder";
    }

    @PostMapping("/saveBackPorder")
    public String savePorder(@ModelAttribute("porder") Porder porder) {        
        // save employee to database
    	//System.out.println("error"+porder.toString()); //test
    	porderService.savePorder(porder);
        return "Backporderlist";
    }

    @GetMapping("/showBackPorderForUpdate/{id}")
    public String showBackPorderForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get employee from the service
    	Porder porder = porderService.getPorderById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("porder", porder);
        return "Backupdate_porder";
    }

    @GetMapping("/deletebackporder/{id}")
    public String deleteBackMember(@PathVariable(value = "id") long id, Model model) {
        // call delete employee method 
        this.porderService.deletePorderById(id);
        return "Backporderlist";
    }
    
    @GetMapping("/searchBackPorder")
    public String searchMemberBar(Porder proder, Model model, String keyword) {
		if (keyword != null) {
			List<Porder> plist = porderService.getByKeyword(keyword);
			model.addAttribute("listPorders", plist);
		} else {
			List<Porder> plist = porderService.getAllPorders();
			model.addAttribute("listMembers", plist);
		}
		return "Backporderlist";
	}
}
    
