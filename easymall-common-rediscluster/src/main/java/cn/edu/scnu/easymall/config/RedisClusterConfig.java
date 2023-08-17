package cn.edu.scnu.easymall.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
@Configuration
@ConfigurationProperties(prefix="spring.redis.cluster")

public class RedisClusterConfig {
	private List<String> nodes;
	private Integer maxTotal;
	private Integer maxIdle;
	private Integer minIdle;
	//初始化JedisCluster的方法
	@Bean 
	public JedisCluster initJedisCluster(){
		//收集节点信息
		Set<HostAndPort> set=new HashSet<HostAndPort>();
//		for (String node : nodes) {
//			//node="192.168.47.128:8000"
//			String host=node.split(":")[0];
//			Integer port=Integer.
//					parseInt(node.split(":")[1]);
//			set.add(new HostAndPort(host,port));
//		}
//		set.add(new HostAndPort("127.0.0.1",6379));
		set.add(new HostAndPort("127.0.0.1",6380));
//		set.add(new HostAndPort("127.0.0.1",6381));

		GenericObjectPoolConfig config=new GenericObjectPoolConfig();
		config.setMaxTotal(200);
		config.setMaxIdle(8);
		config.setMinIdle(2);
		return new JedisCluster(set,config);
	}
	
	
	
	
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

	
}
