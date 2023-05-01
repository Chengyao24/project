package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.demo.model.Member;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.Porder;
import com.example.demo.service.MemberService;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.PorderService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PorderService porderService;
	

	@GetMapping("/home")
	public String openHome(Model model, HttpServletRequest request) {
		// ModelAndView model = new ModelAndView("home");

		HttpSession session = request.getSession();

		if (session.getAttribute("member") != null) {
			// 如果已經登入，添加一些額外的內容到產品清單中
			Member member = (Member) session.getAttribute("member");
			model.addAttribute("memberId", member.getMemberId());
			model.addAttribute("memberPhone", member.getMobile());
			model.addAttribute("memberPassword", member.getPassword());
		}

		return "home";
	}

	@GetMapping("/admin")
	public String viewHomePage(Model model) {
		model.addAttribute("listMembers", memberService.getAllMember());
		return "index";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		Member member = memberService.getMemberByMemberId(id);

		model.addAttribute("member", member);
		return "update_member";
	}

	@PostMapping("/updateMember")
	public String saveMember(@ModelAttribute("member") Member member) {

		memberService.saveMember(member);

		return "redirect:/";
	}

	@GetMapping("/deleteMember/{id}")
	public String deleteMember(@PathVariable(value = "id") long id) {

		// call delete employee method
		this.memberService.deleteMemberById(id);
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		session.invalidate();
		redirectAttributes.addFlashAttribute("message", "已成功登出");
		return "redirect:/home";
	}

//    @GetMapping("/product")
//    public ModelAndView showProductView() {
//        ModelAndView model = new ModelAndView("product");
//        
//        return model;
//    }
	
	@GetMapping("/history")
	public String showHistory(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();

		Member member = (Member) session.getAttribute("member");
		long memberid= member.getMemberId();
		
		List<Porder> porderlist=porderService.getPorderByMemberId(memberid);
		
		model.addAttribute("data",porderlist);
		model.addAttribute("member", member);
		return "history";
	}
}
