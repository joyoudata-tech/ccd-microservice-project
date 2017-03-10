package com.joyoudata.authService.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
public class OAuth2ServerConfiguration {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static final String RESOURCE_ID = "joyouResource";
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private TokenStore tokenStore;
//		
//		@Autowired
//		private AuthenticationManager authenticationManager;
//		
//		@Bean
//		protected AuthenticationEntryPoint authenticationEntryPoint() {
//			OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
//			return authenticationEntryPoint;
//		}
//		
//		@Bean
//		protected ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter() {
//			ClientCredentialsTokenEndpointFilter filer = new ClientCredentialsTokenEndpointFilter();
//			filer.setAuthenticationManager(authenticationManager);
//			return filer;
//		}
//		
//		@Bean
//		protected AccessDeniedHandler oauthAccessDeniedHandler() {
//			AccessDeniedHandler oauthAccessDeniedHandler = new OAuth2AccessDeniedHandler();
//			return oauthAccessDeniedHandler;
//		}
//		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID);
			resources.tokenStore(tokenStore);
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
			.requestMatcher(new AntPathRequestMatcher("/userinfo"))
			.authorizeRequests().anyRequest()
			.authenticated();
		}	
		
		

//		@Override
//		public void configure(HttpSecurity http) throws Exception {
//			
//			http
//			.formLogin().loginPage("/login").permitAll()
//			.and()
//			.requestMatchers()
//			.antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
//			.and()
//			.authorizeRequests()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.csrf()
//			.csrfTokenRepository(csrfTokenRepository())
//			.and()
//			.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
//						
//			http.authorizeRequests()
//			.antMatchers("/oauth/token")
//			.anonymous()
//			.and()
//			.httpBasic().authenticationEntryPoint(authenticationEntryPoint())
//			.and()
//			.exceptionHandling().accessDeniedHandler(oauthAccessDeniedHandler())
//			.and()
//			.addFilterAfter(clientCredentialsTokenEndpointFilter(), BasicAuthenticationFilter.class)
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			
//			http.authorizeRequests()
//			.antMatchers("/oauth/check_token").access("#oauth2.hasScope('trust')")
//			.and()
//			.httpBasic().authenticationEntryPoint(authenticationEntryPoint())
//			.and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			
//			http
//			.authorizeRequests()
//			.antMatchers("/**").access("#oauth2.hasScope('read')")
//			.and()
//			.exceptionHandling().accessDeniedHandler(oauthAccessDeniedHandler());
			// @formatter:off
		}
		
		@Configuration
		@EnableAuthorizationServer
		protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
			
			private static String REALM = "MY_OAUTH_REALM";
			
			@Autowired
		    private DataSource dataSource;

			@Autowired
			@Qualifier("authenticationManagerBean")
			private AuthenticationManager authenticationManager;
			
			@Autowired
			private ClientDetailsService clientDetailService;
			
			@Autowired
			private PasswordEncoder passwordEncoder;
			
			@Bean
			public JdbcTokenStore tokenStore() {
				return new JdbcTokenStore(dataSource);
			}
			
			@Bean
			protected AuthorizationCodeServices authorizationCodeServices() {
				return new JdbcAuthorizationCodeServices(dataSource);
			}
			
			@Bean
			protected AuthorizationServerTokenServices tokenService() {
				DefaultTokenServices tokenServices = new DefaultTokenServices();
				tokenServices.setTokenStore(tokenStore());
				tokenServices.setSupportRefreshToken(true);
				return tokenServices;
			}
			
			@Bean
			protected ClientDetailsService clientDetailsService() {
				return new JdbcClientDetailsService(dataSource);
			}
			
			@Bean
			protected UserDetailsService clientUserService() {
				UserDetailsService userService = new ClientDetailsUserDetailsService(clientDetailsService());
				return userService;
			}
			
			@Bean
		    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
		        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		        handler.setTokenStore(tokenStore);
		        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailService));
		        handler.setClientDetailsService(clientDetailService);
		        return handler;
		    }
						
		    @Bean
		    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		        TokenApprovalStore store = new TokenApprovalStore();
		        store.setTokenStore(tokenStore);
		        return store;
		    }
									
			@Override
			public void configure(AuthorizationServerSecurityConfigurer security)
					throws Exception {
				security.tokenKeyAccess("permitAll");
				security.checkTokenAccess("isAuthenticated()");
				security.passwordEncoder(passwordEncoder);
				security.realm(REALM + "/client");
			}

			@Override
			public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
				clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
			}

			@Override
			public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {				
				endpoints
				.tokenStore(tokenStore())
				.userApprovalHandler(userApprovalHandler(tokenStore()))
				.authenticationManager(authenticationManager)
				.authorizationCodeServices(authorizationCodeServices()).approvalStoreDisabled()
				.setClientDetailsService(clientDetailService);
			}

		}

	}

