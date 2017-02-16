package authService.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathBeanPostProcessor;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;

@EnableConfigurationProperties
@Configuration
public class AuthConfig {
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
