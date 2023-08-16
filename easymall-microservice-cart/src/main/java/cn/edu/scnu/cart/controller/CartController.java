package cn.edu.scnu.cart.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Cart;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.cart.service.CartService;
@RequestMapping("/cart/manage")
@RestController
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/query")
	public List<Cart>queryMyCart(String userId){
		//对userId字符串做判空处理
		if(!StringUtils.isNotEmpty(userId)) {
			return null;//userId为空，交给前端做后续处理
		}
		return cartService.queryMyCart(userId);
	}
	
	@RequestMapping("/save")
	public SysResult cartSave(Cart cart) {
		try {
			cartService.cartSave(cart);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
		
	}
	
	
	@RequestMapping("/update")
	public SysResult updateNum(Cart cart) {
		try {
			cartService.updateNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
		
	}
	
	@RequestMapping("/delete")
	public SysResult deleteCart(Cart cart) {
		try {
			cartService.deleteCart(cart);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
		
	}
	
}
