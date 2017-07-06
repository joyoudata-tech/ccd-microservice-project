package com.joyoudata.authService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.joyoudata.authService.domain.ClientDetail;
import com.joyoudata.authService.domain.LDAPContextProperty;
import com.joyoudata.authService.domain.Oauth2ServerProperty;
import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.repository.ClientDetailRepository;
import com.joyoudata.authService.service.UserService;
import com.joyoudata.authService.utils.StringConvertUtils;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.joyoudata.authService.repository")
@SessionAttributes("authorizationRequest")
@ComponentScan
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServerMain extends WebMvcConfigurerAdapter{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/oauth/confirm_access").setViewName("authorize");
	}
	
	@RequestMapping("/userinfo")
    public SimpleUser user(Principal user) {
		//重新设定User
		List<String> roles = new ArrayList<>();
		Collection<GrantedAuthority> authorities = ((OAuth2Authentication) user).getAuthorities();
		authorities.stream().forEach(r -> {
			roles.add(r.getAuthority());
		});
        return new SimpleUser(user.getName(),roles);
    }
	
	class SimpleUser {

        String username;
        List<String> authorities;

        SimpleUser(String username, List<String> authorities) {
            this.username=username;
            this.authorities =authorities;
        }

        public String getUsername() {
            return username;
        }

        public List<String> getAuthorities() {
            return authorities;
        }
    }
	
    public static void main(String[] args) {
    	SpringApplication.run(AuthServerMain.class, args);
    }
    
    @Autowired
    private LDAPContextProperty ldapProterties;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapProterties.getUrl());
        contextSource.setBase(ldapProterties.getBase());
        contextSource.setUserDn(ldapProterties.getUserDn());
        contextSource.setPassword(ldapProterties.getPassword());
        contextSource.afterPropertiesSet();
        return contextSource;
    }
    
    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
    	LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
    	ldapTemplate.setIgnorePartialResultException(true);        
    	return ldapTemplate;
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private Oauth2ServerProperty oauth2Server;
    
    @Bean
    CommandLineRunner onStartup(UserService userService, ClientDetailRepository clientService) {
        return (String... args) -> {
        	;
            userService.deleteAll();
            
            List<Map<String,String>> oauth_users = oauth2Server.getUsers();
            
            oauth_users.stream().forEach(u -> {
            	User adminUser = new User();
                adminUser.setEmail(u.get("email"));
                adminUser.setUsername(u.get("username"));
                String encode = passwordEncoder.encode(u.get("password"));
                adminUser.setPassword(encode);
                userService.saveUser(adminUser);
                List<String> roles = StringConvertUtils.stringToList(u.get("role"));
                userService.saveUserRolesWithUserName(u.get("username"), roles);
            });
            
            List<Map<String,Object>> oauth_clients = oauth2Server.getClients();
            
            oauth_clients.stream().forEach(c -> {
            	 ClientDetail authClient = new ClientDetail();
                 authClient.setClientId((String) c.get("clientId"));
                 authClient.setResourceIds((String) c.get("resourceIds"));
                 authClient.setClientSecret(passwordEncoder.encode((String) c.get("clientSecret")));
                 authClient.setRefreshTokenValidity((Integer)c.get("refreshTokenValidity"));
                 authClient.setAccessTokenValidity((Integer) c.get("accessTokenValidity"));
                 authClient.setAuthorities((String) c.get("authorities"));
                 authClient.setAuthorizedGrantTypes((String) c.get("authorizedGrantTypes"));
                 authClient.setScope((String) c.get("scope"));
                 authClient.setScoped((Boolean) c.get("scoped"));
                 authClient.setSecretRequired((Boolean) c.get("secretRequired"));
                 authClient.setAutoapprove((Boolean) c.get("autoapprove"));
                 clientService.save(authClient);
            });          
        };
    }
}