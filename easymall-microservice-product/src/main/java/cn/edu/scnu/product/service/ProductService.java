package cn.edu.scnu.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymall.common.pojo.Product;
import com.easymall.common.vo.EasyUIResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.scnu.product.mapper.ProductMapper;
import redis.clients.jedis.Jedis;

@Service  
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	
	public EasyUIResult productPageQuery(Integer page, Integer rows) {
		// 封装数据到EasyUIResult对象
		// 1.创建一个返回的对象,将查询数据set进去然后返回
		EasyUIResult result=new EasyUIResult();
		// 2封装第一个属性 total 的数量
		Integer total=productMapper.queryTotal();
		
		// 3封装第二个属性List<Product> pList
		
		// 根据页数计算起始位置
		Integer start=(page-1)*rows;
		List<Product> pList=productMapper.queryByPage(start,rows);
		// 4封装对象属性
		
		result.setTotal(total);
		result.setRows(pList);
		return result;
	}

	public String queryById(String productId) {
		// TODO Auto-generated method stub
		String productKey="product_"+productId;
		Jedis jedis=new Jedis("localhost",6379,20000);
		
		if(jedis.exists(productKey)) {
			return jedis.get(productKey);
		}else {
			ObjectMapper mapper=new ObjectMapper();
			String productJson;
			try {
				productJson=mapper.writeValueAsString
						(productMapper.queryById(productId));
				jedis.setex(productKey, 600, productJson);
				return productJson;
			}catch(Exception e) {
				e.printStackTrace();
				return "";
			}
		}
	}

	public void productSave(Product product) {
		// TODO Auto-generated method stub
		product.setProductId(UUID.randomUUID().toString());
		productMapper.productSave(product);
	}

	public void productUpdate(Product product) {
		// TODO Auto-generated method stub
		productMapper.productUpdate(product);
	}

}
