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
import com.example.demo.model.Member;
import com.example.demo.service.BackAdminService;
import com.example.demo.service.BackMemberService;
import com.example.demo.service.BackMemberServiceImpl;


@Controller
public class BackMemberController {
	
	@Autowired
	private HttpSession session;

    @Autowired
    private BackMemberService memberService;
    
    @Autowired
    private BackAdminService adminService;
    
    public static String nowCode =BackController.userCode;


    // 處理 GET 請求，取得所有使用者
    /*@GetMapping("/memberList")
    public String getUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("listMembers", memberService.getAllMembers());
    	Page<Member> memPage = memberService.getUsersPageable(page);
        model.addAttribute("mems", memPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", memPage.getTotalPages());
        return "memberlist"; // 返回 Thymeleaf 模板名稱
    }*/
    
    // display list of employees
    @GetMapping("/memberList")
    public String viewHomePage(Model model) {
    	Admin admins = adminService.getAdminCode(nowCode);
        session.setAttribute("admin", admins);//session是抓到資料給sever，讓整個網站都可以取得該資料(每個頁面要用addAttribute自己抓資料)
        model.addAttribute("admins", admins);//每個頁面要用addAttribute自己抓session的資料
        
        model.addAttribute("listMembers", memberService.getAllMembers());
        return "Backmemberlist";
    }

    @GetMapping("/showBackMemberForm")
    public String showNewMemberForm(Model model) {  
        // create model attribute to bind form data
    	Member member = new Member();
        model.addAttribute("member", member);
        return "Backnew_member";
    }

    @PostMapping("/saveBackMember")
    public String saveMember(@ModelAttribute("member") Member member) {        
        // save employee to database
    	memberService.saveMember(member);
        return "Backmemberlist";
    }

    @GetMapping("/showBackMemberForUpdate/{id}")
    public String showBackMemberForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get employee from the service
    	Member member = memberService.getMemberById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("member", member);
        return "Backupdate_member";
    }

    @GetMapping("/deletebackmember/{id}")
    public String deleteBackMember(@PathVariable(value = "id") long id, Model model) {
        // call delete employee method 
        this.memberService.deleteMemberById(id);
        return "Backmemberlist";
    }
    
    @GetMapping("/searchBackMember")
    public String searchMemberBar(Member member, Model model, String keyword) {
		if (keyword != null) {
			List<Member> mlist = memberService.getByKeyword(keyword);
			model.addAttribute("listMembers", mlist);
		} else {
			List<Member> mlist = memberService.getAllMembers();
			model.addAttribute("listMembers", mlist);
		}
		return "Backmemberlist";
	}
    
}
