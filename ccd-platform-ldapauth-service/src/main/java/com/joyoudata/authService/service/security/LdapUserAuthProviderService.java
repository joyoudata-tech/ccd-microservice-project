package com.joyoudata.authService.service.security;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.joyoudata.authService.domain.UserDetail;
import com.joyoudata.authService.service.UserDetailService;
import com.joyoudata.authService.utils.LDAPUtils;

/**
 * Ldap用户认证提供 服务
 * */
@Component
public class LdapUserAuthProviderService implements AuthenticationProvider {

	@Autowired
	private UserDetailService userService;
	
	@Autowired
	private UserAuthConfigService authConfigService;
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Autowired
	private LDAPUtils ldapUtils;
	
	@Value("${ldap.contextSource.email}")
	private String emailField;
	
	@Value("${ldap.contextSource.searchScope}")
	private String searchScope;
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		//匹配email字段
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(emailField, email));
		//ldap 认证
		boolean authenticate = ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.toString(), password);
		UserDetail user = userService.findUserByEmail(email);
		if (authenticate) {
			//原数据库中没有这个user时，则将ldap用户映射到数据库中去
			if (user == null) {
				SearchControls controls = new SearchControls();
				//设置searchScope
				if (searchScope.equalsIgnoreCase("substree")) {
					controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				}else{
					controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
				}
				List users = ldapTemplate.search(LdapUtils.emptyLdapName(), filter.toString(), controls, ldapUtils);
				if (!users.isEmpty()) {
					//将ldap用户存入应用数据库中
					user = (UserDetail) users.get(0);
					userService.saveUser(user);
				}
			}
			//赋予用户权限
			List<GrantedAuthority> rights = authConfigService.getRight(user);
			return authConfigService.signRolesInUser(user, rights);
		}
		throw new AuthenticationException("Your ldap authentication may be not correct,your application not throw the auth."){};
	}

	@Override
	public boolean supports(Class<?> authentication) {		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
