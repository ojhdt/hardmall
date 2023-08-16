package cn.edu.scnu.seckill.controller;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Seckill;
import com.easymall.common.pojo.Success;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.seckill.config.RabbitmqConfig;
import cn.edu.scnu.seckill.mapper.SucMapper;
import cn.edu.scnu.seckill.service.SecService;

@RestController
public class SecController {

	@Autowired
	private SecService secService;
	
	@Autowired
	private SucMapper sucMapper;
	
	//查询所有可以秒杀的商品数据
	@RequestMapping("/seckill/manage/list")
	public List<Seckill> queryAll(){
		
		return secService.queryAll();
	}
	
	//根据秒杀商品id查询详情商品信息
	@RequestMapping("/seckill/manage/detail")
	public Seckill queryOne(String seckillId){
		return secService.queryOne(seckillId);
	}
	
	//接收秒杀商品的请求
	@Autowired
	private RabbitTemplate client;
	@RequestMapping("seckill/manage/{seckillId}")
		public SysResult startSeckill(@PathVariable Long seckillId) {
		//确定用户身份， userId， usernameuser Phone
		//前端没有实现js.限制未登录的秒杀，
		//此处暂时用随机生成的手机号作为userId
		String userId="180888"+RandomUtils.nextInt(10000,99999) ;
		//发送消息确定用户身份，秒杀商品消息
		String msg=userId+"/"+seckillId;
		client.convertAndSend(RabbitmqConfig.exName,RabbitmqConfig.routingKey,msg); 
		return SysResult.ok() ;
	}
	
	@RequestMapping("seckill/manage/{seckillId}/userPhone")
	public List<Success>querySuccess(@PathVariable String seckillId){
		//System.out.println("id传进来没："+seckillId);
		if("".equals(seckillId)||seckillId==null) seckillId="1";
		
		Long seckillIdLong =Long.parseLong(seckillId);
		return sucMapper.queryAllSuccess(seckillIdLong);
	}
}
