package cn.edu.scnu.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.easymall.common.pojo.Product;

public interface ProductMapper {
	//查找商品总数量
	Integer queryTotal();
	//交给sql语句的参数简单来说只有1个，两个，怎么办
	/*
	 * 多个参数传递给sqlsession通过#{}来进行拼接时，可以用注解@Param来定义
	 * 参数变量名称
	 */
	
	List<Product> queryByPage(@Param("start")Integer page, 
			@Param("rows")Integer rows);

	Product queryById(String productId);

	void productSave(Product product);

	void productUpdate(Product product);

}
