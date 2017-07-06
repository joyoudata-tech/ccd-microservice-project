package com.joyoudata.authService.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID);
			resources.tokenStore(tokenStore);
		}
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			//单点登陆，用户授权模式
			http
			.requestMatcher(new AntPathRequestMatcher("/userinfo"))
			.authorizeRequests().anyRequest()
			.authenticated();
			
			//用户权限：USER,ADMIN
//			http
//			.requestMatcher(new AntPathRequestMatcher("/users/**"))
//			.authorizeRequests().antMatchers(HttpMethod.GET).access("hasRole('USER')")
//			.and()
//			.authorizeRequests().antMatchers(HttpMethod.POST).access("hasRole('ADMIN') and hasRole('USER')")
//			.and()
//			.authorizeRequests().antMatchers(HttpMethod.PUT).access("hasRole('ADMIN') and hasRole('USER')")
//			.and()
//			.authorizeRequests().antMatchers(HttpMethod.DELETE).access("hasRole('ADMIN') and hasRole('USER')")
//			.anyRequest().authenticated();
		}	
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

