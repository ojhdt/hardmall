package cn.edu.scnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.edu.scnu.seckill.mapper")
public class StartSeckillCenter {

	public static void main(String[] args) {
		SpringApplication.run(StartSeckillCenter.class,args);
	}
}
