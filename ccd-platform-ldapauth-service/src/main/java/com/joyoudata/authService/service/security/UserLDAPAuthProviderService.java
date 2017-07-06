package com.joyoudata.authService.service.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.service.UserService;
import com.joyoudata.authService.utils.LDAPMapperUtil;

/**
 * Ldap用户认证提供 服务
 * */
@Component
public class UserLDAPAuthProviderService implements AuthenticationProvider{

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAuthConfigService userAuthConfigService;
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Autowired
	private LDAPMapperUtil mapperAttributes;
	
	private String DEFAULT_UAA_ROLE = "ROLE_USER";
	
	@Override
	public Authentication authenticate(Authentication a) throws AuthenticationException {
		String username = a.getName();
		String password = a.getCredentials().toString();
		Filter filter = new EqualsFilter("sAMAccountName", username);
				
		boolean authenticate = ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.encode(), password);
		User user = userService.findUserBySAMAccountName(username);
		if (authenticate) {
			if (user == null) {
				List<User> users = ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), mapperAttributes);
				if (!users.isEmpty()) {
					user = (User) users.get(0);
					user.setDateCreated(new Date());
					userService.saveUser(user);
					List<String> roles = new ArrayList<String>();
					roles.add(DEFAULT_UAA_ROLE);
					userService.saveUserRolesWithUserName(username, roles);
				}
			}
			List<GrantedAuthority> rights = userAuthConfigService.getRights(user);
			return userAuthConfigService.signInUser(user, rights);
		}
		throw new AuthenticationException("Your ldap authentication may be not correct,your application not throw the auth."){
			private static final long serialVersionUID = 1172114877967831738L;};
	}

	@Override
	public boolean supports(Class<?> type) {		
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(type);
	}
}
