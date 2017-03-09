package org.ccd.platform.openproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * author:qzc
 * spring boot 主函数
 */

@EnableWebMvc
@ComponentScan({"org.ccd.platform.openproject"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class App 
{
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
    }
}
