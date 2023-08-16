package cn.edu.scnu.cart.mapper;

import java.util.List;

import com.easymall.common.pojo.Cart;

public interface CartMapper {
	List<Cart> queryMyCart(String userId);
	void updateNum(Cart exist);
	void saveCart(Cart cart);
	Cart queryOne(Cart cart);
	
	void deleteCart(Cart cart);
}
