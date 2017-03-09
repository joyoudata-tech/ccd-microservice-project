package org.ccd.platporm.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@EnableEurekaClient
@ComponentScan
public class ApiGatewayServer 
{
    public static void main(String[] args)
    {
        SpringApplication.run(ApiGatewayServer.class, args);
    }
}
