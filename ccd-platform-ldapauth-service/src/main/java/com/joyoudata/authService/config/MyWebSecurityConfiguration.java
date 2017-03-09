package com.joyoudata.authService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.joyoudata.authService.service.security.UserAuthConfigService;
import com.joyoudata.authService.service.security.UserAuthProviderService;
import com.joyoudata.authService.service.security.UserLDAPAuthProviderService;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	  return super.authenticationManagerBean();
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//    	.csrf()
//		.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
//		.disable();
//	}
	
	

	@Autowired
	private UserLDAPAuthProviderService ldapAuthProvider;
	
	@Autowired
	private UserAuthProviderService userAuthProvider;
	
	@Autowired
	private UserAuthConfigService userAuthService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder
		.authenticationProvider(ldapAuthProvider)
		.authenticationProvider(userAuthProvider)
		.userDetailsService(userAuthService);
    }


}
