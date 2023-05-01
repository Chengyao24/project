package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

import javax.servlet.http.HttpSession;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;

@Controller
@RequestMapping(value = "/member") // 這裡是根URL
public class UserController {

	@Autowired
	private HttpSession session;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MemberService memberService;

	@GetMapping("/login")
	public ModelAndView openLogin() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {
		boolean isValid = memberService.checkAccountAndPassword(mobile, password);
		if (isValid) {

			ModelAndView model = new ModelAndView("redirect:/home");
			Member member = memberService.getMemberByMobile(mobile);

			model.addObject("loggedIn", true);
			session.setAttribute("member", member);
			return model;
		} else {
			// 登入失敗，顯示錯誤訊息
			ModelAndView model = new ModelAndView("login");
			model.addObject("errorMessage", "帳號或密碼錯誤！");
			return model;
		}
	}

	@GetMapping("/UserIndex")
	public ModelAndView toFormForUpdate(@SessionAttribute("member") Member member) {
		System.out.println(member.toString());

		ModelAndView model = new ModelAndView("UserIndex");
		model.addObject("member", member);
		return model;
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		Member member = memberService.getMemberByMemberId(id);;

		model.addAttribute("member", member);

		return "update_member2";
	}

	@PostMapping("/updateMember2")
	public String user_saveMember(@ModelAttribute("member") Member member) {

		memberService.saveMember(member);

		return "redirect:/home";
	}

	@GetMapping("/showForm")
	public String showNewMemberForm(Model model) {

		Member member = new Member();

		model.addAttribute("member", member);
		return "new_member";
	}

	@PostMapping("/saveMember")
	public ModelAndView saveMember(@ModelAttribute("member") Member member) {

		if (memberService.existsByMobile(member.getMobile())) {
			ModelAndView model = new ModelAndView("new_member");
			model.addObject("errorMessage", "已註冊的手機號碼");
			return model;
		} else {
			memberService.saveMember(member);

			// 寄送註冊成功的信件
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(member.getEmail());
			message.setSubject("註冊成功");
			message.setText("您已成功註冊會員，謝謝您的加入！");
			mailSender.send(message);

			ModelAndView model = new ModelAndView("login");
			return model;
		}

	}
	 //忘記密碼
	  @GetMapping("/forgotPassword")
	    public ModelAndView showForgotPasswordForm() {
		  ModelAndView model=new ModelAndView("forgotPassword");
	        return model;
	    }

	    @PostMapping("/forgotPassword")
	    public String processForgotPasswordForm(@RequestParam("email") String userEmail, Model model) {

	       Member member = memberService.getMemberByEmail(userEmail); 

	        if (member == null) {
	            model.addAttribute("errorMessage", "此 email 尚未註冊！");
	        } else {
	            // 產生一個新的隨機密碼
	            String newPassword = generateNewPassword();

	            // 更新使用者密碼
	            member.setPassword(newPassword);
	            memberService.saveMember(member);

	            // 寄送 email 通知使用者新密碼
	            sendNewPasswordEmail(member.getEmail(), newPassword);

	            model.addAttribute("successMessage", "已發送一封 email 至您的信箱，請查收新密碼！");
	        }

	        return "forgotPassword";
	    }
	    
	 // 產生一個新的隨機密碼
	    private String generateNewPassword() {
	        String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	        StringBuilder newPassword = new StringBuilder();
	        Random random = new Random(); //random是util中用來產生隨機數字的類別
	        for (int i = 0; i < 8; i++) {
	            newPassword.append(charPool.charAt(random.nextInt(charPool.length())));
	        }
	        return newPassword.toString();
	    }
	    
	    // 寄送 email 通知使用者新密碼
	    	private void sendNewPasswordEmail(String userEmail, String newPassword) {
	    	    SimpleMailMessage message = new SimpleMailMessage();
	    	    message.setTo(userEmail);
	    	    message.setSubject("New Password");
	    	    message.setText("您的新密碼為: " + newPassword);
	    	    mailSender.send(message);
	    	}

	    }



