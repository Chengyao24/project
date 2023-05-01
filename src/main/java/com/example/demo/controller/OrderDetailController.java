package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.demo.model.*;
import com.example.demo.service.*;

@Controller
public class OrderDetailController {
	@Autowired
	private OrderDetailService os;

	@Autowired
	private ProductService ps;

	@Autowired
	private OrderDetailRepository _orderDetail;

	@Autowired
	private MemberRepository _member;

	@Autowired
	private PorderRepository _porder;

	@Autowired
	private ProductRepository _product;

	@Autowired
	private MemberService ms;

	@Autowired
	private PorderService pds;

	// 購物車暫存
	public static List<OrderDetail> cartList;
	
	public static Double paymoney;

	// 顯示購物車(結構)
	@GetMapping("/cart")
	public String cart(Model model) {
		//, HttpServletRequest request
//		HttpSession session=request.getSession();
//		Member member = (Member)session.getAttribute("member");
//		long member_id = member.getMemberId();
		
		
		List<OrderDetail> cartItems = os.getAllOrderDetail();
		
		double totalPrice = cartItems.stream()
				.mapToDouble(orderDetail -> orderDetail.getPrice() * orderDetail.getQuantity()).sum();
		paymoney = totalPrice;
		
		//cartItems = cartItems.stream().filter(m -> m.getTest() == (int)member_id).collect(Collectors.toList());
		model.addAttribute("listCarts", cartItems);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("data", new Porder());

		return "Cart";// 到時候在看購物車html叫甚麼
	}

	// 購物車結帳+儲存
	@PostMapping("/cartpay/Save")
	public String Save(Model model, @ModelAttribute("data") Porder porder, HttpServletRequest request,
			OrderDetail orderDetail) {

		HttpSession session = request.getSession();

		List<Porder> oList = _porder.findAll();
		

		Member member = (Member) session.getAttribute("member");

		long member_id = member.getMemberId();

		oList = oList.stream().filter(m -> m.getMember().getMemberId() == member_id).collect(Collectors.toList());
		// 把oList抓到的資料丟到一個stream()裡去篩選掉不等於登入會員memberid的資料，再把符合的資料轉成一個List，最後丟回去oList

		String kw = "已完成";
		String kw2="製作中";
		if (kw != null && kw2!=null) {
			oList = oList.stream().filter(m -> !m.getStatus().equals(kw)).collect(Collectors.toList());
			// 把oList抓到的資料丟到一個stream()裡去篩選掉狀態不等於已完成的資料，再把符合的資料轉成一個List，最後丟回去oList
		}

		Porder O = new Porder();

		if (oList.size() != 0) {

			O = oList.get(oList.size() - 1);
			// 抓資料庫最後一筆資料

		} else {

			O.setMember(member);
			_porder.save(O);
		}

		OrderDetail S = orderDetail;

		Date date = new Date();

		Timestamp pordertime = new Timestamp(date.getTime());

		O.setCreateDate(pordertime);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHHmmss");

		O.setSn("Pp" + formatter.format(date));

		if (session.getAttribute("member") != null) {
			O.setMember(member);
		}
		O.setPaymoney(paymoney);
		O.setType(porder.getType());
		O.setStatus("未完成");// 預設都是已完成訂單，因為沒有判斷是否完成的機制，所以都預設為已完成(按結帳就完成訂單的意思)
		
		double total ;
		//O.setTotal("");
		
		_porder.save(O);
		S.setPorder(O);

		try {

			switch (porder.getType()) {

//			case 1: // 綠界
//				return "/Ecpay";
			case 0:
				return "/Msg";
			default:
				return "/preorder";
			}

		} catch (Exception e) {
			return "/preorder";
		}
	}

	// 選購商品到購物車
	@GetMapping("/saveCart/{id}")
	public String saveCart(@PathVariable Long id, OrderDetail orderDetail, HttpServletRequest request) {

		Product product = ps.getProductById(id);

		cartList = os.getAllOrderDetail();

		// 獲取購物車列表
		boolean exists = false;
		for (OrderDetail c : cartList) {

			if (c.getProductName().equals(product.getPname())) { // 判斷購物車是否已存在該商品
				c.setQuantity(c.getQuantity() + 1); // 存在則數量+1
				c.setTotal(c.getPrice() * c.getQuantity());
				c.setProduct(product);

				Porder o = new Porder();
				orderDetail.setPorder(o);

				os.saveOrderDetail(c);
				exists = true;

				break;

			}
		}

		if (!exists) { // 不存在則新增一條
			
			HttpSession session = request.getSession();
			Member member = (Member) session.getAttribute("member");

			long member_id = member.getMemberId();

			orderDetail.setProductName(product.getPname());
			orderDetail.setPrice(product.getPrice());
			orderDetail.setQuantity(1);
			orderDetail.setTotal(product.getPrice() * 1);
			orderDetail.setProduct(product);

			List<Porder> oList = _porder.findAll();

			

			oList = oList.stream().filter(m -> m.getMember().getMemberId() == member_id).collect(Collectors.toList());

			String kw = "已完成";
			String kw2 = "製作中";
			if (kw != null && kw2!=null) {
				oList = oList.stream().filter(m -> !m.getStatus().equals(kw)).collect(Collectors.toList());
			}

			Porder o = new Porder();

			if (oList.size() != 0) {

				o = oList.get(oList.size() - 1);

			} else {

				o.setMember(member);
				_porder.save(o);
			}

			orderDetail.setPorder(o);
			os.saveOrderDetail(orderDetail);
		}

		return "redirect:/preorder";
	}

	// 刪除購物車某商品
	@GetMapping("/deleteCart/{Id}")
	public String deleteOrderDetailById(@PathVariable Long Id) {
		this.os.deleteOrderDetailById(Id);
		return "redirect:/cart";
	}
}
