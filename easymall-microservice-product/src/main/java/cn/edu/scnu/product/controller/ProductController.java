package cn.edu.scnu.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Product;
import com.easymall.common.vo.EasyUIResult;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.product.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/product/manage/pageManage")
	public EasyUIResult productPageQuery(Integer page,Integer rows) {
		EasyUIResult result=productService.productPageQuery(page,rows);
		return result;
	}
	//查看商品详情
	@RequestMapping("/product/manage/item/{productId}")
	public String queryById(@PathVariable String productId) {
		
		
		String product=productService.queryById(productId);
		return product;
	}
	//商品新增
	@RequestMapping("/product/manage/save")
	public SysResult productSave(Product product) {
		try {
			productService.productSave(product);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(201, "您已经重复添加商品，添加失败", null);
		}
	}
	
	@RequestMapping("/product/manage/update")
	public SysResult productUpdate(Product product) {
		try {
			productService.productUpdate(product);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}
	//
	
}
