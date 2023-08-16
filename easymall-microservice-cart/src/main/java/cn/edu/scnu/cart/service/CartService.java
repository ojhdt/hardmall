package cn.edu.scnu.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.easymall.common.pojo.Cart;
import com.easymall.common.pojo.Product;

import cn.edu.scnu.cart.mapper.CartMapper;

@Service
public class CartService {

	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private RestTemplate restTemplate;//RPC远程调用本地服务
	
	public List<Cart> queryMyCart(String userId){
		return cartMapper.queryMyCart(userId);
	}
	
	public void cartSave(Cart cart) {
		Cart exist=cartMapper.queryOne(cart);
		if(exist!=null) {//表示购物车有这件商品了
			exist.setNum(exist.getNum()+cart.getNum());
			cartMapper.updateNum(exist);
			//更新一下
		}else {
			Product product = restTemplate
					.getForObject("http://productservice/product/manage/item/"
							+ cart.getProductId(),Product.class);
			cart.setProductPrice(product.getProductPrice());
			cart.setProductName(product.getProductName());
			cart.setProductImage(product.getProductImgurl());
			cartMapper.saveCart(cart);
		}
	}

	public void updateNum(Cart cart) {
		cartMapper.updateNum(cart);
	}

	public void deleteCart(Cart cart) {
		cartMapper.deleteCart(cart);
	}
	
}
