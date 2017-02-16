package authService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication extends WebSecurityConfigurerAdapter{
	
	public static void main(String[] args){
		SpringApplication.run(AuthApplication.class, args);
	}

}
