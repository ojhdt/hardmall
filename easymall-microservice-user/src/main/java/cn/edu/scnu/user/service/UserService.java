package cn.edu.scnu.user.service;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymall.common.constants.PrefixKeys;
import com.easymall.common.pojo.User;
import com.easymall.common.utils.MD5Util;
import com.easymall.common.utils.MapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.scnu.user.mapper.UserMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
//	@Autowired
//	private ShardedJedisPool pool;
	@Autowired
	private JedisCluster jedis;

	
	private ObjectMapper omapper = MapperUtil.MP;
	
	public Integer checkUsername(String userName) {
		// TODO Auto-generated method stub
		return userMapper.checkUsername(userName);
	}

	public void userSave(User user) {
		user.setUserId(UUID.randomUUID().toString());
		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
		userMapper.userSave(user);
		
	}

	public String doLogin(User user) {
		//利用user数据查询数据库，判断登录是否合法
		//获取连接池对象
		//ShardedJedis jedis=pool.getResource();
		try {
			user.setUserPassword(MD5Util.md5(user.getUserPassword()));
			User exist=userMapper.queryUserByNameAndPassword(user);
			if(exist==null) {
				return "";
			}else {
				//为了后续访问能够获取到user对象的数据，需要创建一个key值，
				//将userJson作为value存储在redis中，key值返回给前端
				//前端下次访问就可以携带 生成一个cookie将要携带回去的ticket
				String ticket = PrefixKeys.USER_LOGIN_TICKET+System.currentTimeMillis()+exist.getUserId();
				//每次登录的ticket是随机生成的，每次登录都是唯一标识
				String userJson;
				userJson=omapper.writeValueAsString(exist);
				
				//判断当前用户是否曾经有人登录过
				//String existTicket=jedis.get("user_logined_"+exist.getUserId());
				Long existListLen=jedis.llen(PrefixKeys.USER_LOGINED_CHECK_PREFIX+exist.getUserId());
				//顶替实现.不允许前一个登录的人ticket存在
				//当前登录设备为2台，可以继续完成doLogin，等于不能完成，
				//把第一个人的顶替掉
				if(existListLen>=3){
					jedis.rpop(PrefixKeys.USER_LOGINED_CHECK_PREFIX+exist.getUserId());
				}
				//定义当前客户端登录的信息 userId:ticket
				jedis.lpush(PrefixKeys.USER_LOGINED_CHECK_PREFIX+exist.getUserId(), ticket);
				//jedis.setex("user_logined_"+exist.getUserId(), 60*30,ticket);

				
				jedis.setex(ticket, 60*30, userJson);
				return ticket;
			}
			
	
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public String queryUserJson(String ticket) {
		//ShardedJedis jedis=pool.getResource();
		String userJson="";
		try {
			//首先判断超时剩余时间
			Long leftTime = jedis.pttl(ticket);
			//少于10分钟,延长5分钟
			if(leftTime<1000*60*10l){
				jedis.pexpire(ticket,leftTime+1000*60*5);
			}
			
			userJson=jedis.get(ticket);
			return userJson;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}
	
	
}
