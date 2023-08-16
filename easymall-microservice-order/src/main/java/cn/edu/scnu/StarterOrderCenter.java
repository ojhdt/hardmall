package cn.edu.scnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.edu.scnu.order.mapper")
public class StarterOrderCenter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(StarterOrderCenter.class,args);
	}

}
