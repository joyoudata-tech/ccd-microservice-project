package com.joyoudata.authService.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.stereotype.Component;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.domain.UserRole;

@Component(value = "coreLDAPMapper")
public class LDAPMapperUtil implements AttributesMapper<User> {
	
	private String DEFAULT_RIGHT = "read";

	@Override
	public User mapFromAttributes(Attributes attributes) throws NamingException {
		try {
			User user = new User();
			List<String> rights = new ArrayList<String>();
			rights.add(DEFAULT_RIGHT);
			user.setUsername((String)attributes.get("sAMAccountName").get());
			user.setEmail((String)attributes.get("mail").get());
			user.setFullName((String)attributes.get("name").get());
			user.setMemberOf((String)attributes.get("memberOf").get());
			UserRole userRole = new UserRole(user, DEFAULT_RIGHT);
			Set<UserRole> roles = new HashSet<UserRole>();
			roles.add(userRole);
			user.setUserRole(roles);
			return user;
		} catch (Exception e) {
			throw new NamingException("User doesn't have sAMAccountName.") {
				private static final long serialVersionUID = 8054693650682063415L;
			};
		}
	}

}
