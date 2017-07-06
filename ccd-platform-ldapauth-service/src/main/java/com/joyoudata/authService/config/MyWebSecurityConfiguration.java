package com.joyoudata.authService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

	@Autowired
	private UserLDAPAuthProviderService ldapAuthProvider;

	@Autowired
	private UserAuthProviderService userAuthProvider;

	@Autowired
	private UserAuthConfigService userAuthService;

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.authenticationProvider(ldapAuthProvider).authenticationProvider(userAuthProvider)
				.userDetailsService(userAuthService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// @formatter:off
		http
				.formLogin()
				.loginPage("/login").defaultSuccessUrl("http://127.0.0.1:9097/index.hmtl").permitAll()
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
				.logoutSuccessUrl("/login")

				//.and().logout().invalidateHttpSession(true).deleteCookies("JSESSION")
				.and()
				.requestMatchers()
				.antMatchers("/","/login","/logout","/signout", "/oauth/authorize", "/oauth/confirm_access","/images/**")
				.and()
				.authorizeRequests().anyRequest().authenticated();
		// @formatter:on
	}
}
