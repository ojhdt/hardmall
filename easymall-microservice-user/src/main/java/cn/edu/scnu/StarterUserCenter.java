package cn.edu.scnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.edu.scnu.user.mapper")
public class StarterUserCenter {

	public static void main(String[] args) {
//		try {
//			Class c_user=Class.forName("cn.edu.scnu.StarterUserCenter");
//			SpringApplication.run(c_user, args);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		SpringApplication.run(StarterUserCenter.class, args);
		
	}

}
