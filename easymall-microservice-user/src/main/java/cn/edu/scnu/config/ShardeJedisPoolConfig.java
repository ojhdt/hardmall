package cn.edu.scnu.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
//这个配置包，已经舍弃
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis.shardedpool")
public class ShardeJedisPoolConfig {
	private List<String> nodes;
	private Integer maxTotal;
	private Integer maxIdle;
	private Integer minIdle;
	
	
	public List<String> getNodes() {
		return nodes;
	}


	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}


	public Integer getMaxTotal() {
		return maxTotal;
	}


	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}


	public Integer getMaxIdle() {
		return maxIdle;
	}


	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}


	public Integer getMinIdle() {
		return minIdle;
	}


	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}


	
	//@Bean
	public ShardedJedisPool sjPoolInit() {
		// 利用属性完成连接池的初始化
		// 第一步 收集节点初始化信息
		List<JedisShardInfo> infoList= new ArrayList<JedisShardInfo>();
		for(String node: nodes) {
			String ip = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]);
			infoList.add(new JedisShardInfo(ip, port));
		}
		//封装一个config，设置最大连接数最大空闲
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		return new ShardedJedisPool(config, infoList);
	}
}
