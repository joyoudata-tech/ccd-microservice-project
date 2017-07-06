package service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaServer
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class Discovery 
{
    public static void main( String[] args ){
        SpringApplication.run(Discovery.class, args);
    }
}
