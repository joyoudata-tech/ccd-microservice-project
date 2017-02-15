package authService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathBeanPostProcessor;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties
public class AuthApplication {
	
	public static void main(String[] args){
		SpringApplication.run(AuthApplication.class, args);
	}
	
	@Bean
	public BaseLdapPathBeanPostProcessor baseLdapPathBeanPostProcessor() {		
		return new BaseLdapPathBeanPostProcessor();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "ldap.contextSource")
	public LdapContextSource contextSource() {
		LdapContextSource contextSource = new LdapContextSource();
        return contextSource;
	}
	
	@Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
    
    @Bean
    public LdapShaPasswordEncoder ldapShaPasswordEncoder() {
    	return new LdapShaPasswordEncoder();
    }

}
