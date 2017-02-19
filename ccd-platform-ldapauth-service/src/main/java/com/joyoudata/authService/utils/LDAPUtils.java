package com.joyoudata.authService.utils;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.stereotype.Component;

import com.joyoudata.authService.domain.UserDetail;

@SuppressWarnings("rawtypes")
@Component(value = "coreLDAPMapper")
public class LDAPUtils implements AttributesMapper {
	
	@Value("${ldap.contextSource.email}")
	private String mailField;

	@SuppressWarnings("serial")
	@Override
	public UserDetail mapFromAttributes(Attributes attributes) throws NamingException {
		try {
			//UserDetail 转换成LDAP内的属性
			UserDetail user = new UserDetail();
			String email = null;
			if (attributes.get(mailField) != null) {
				email = attributes.get(mailField).get().toString();
			}
			if (email == null) {
				throw new NamingException("User doesn't have email.") {
				};
			}
			user.setEmail(email.toLowerCase());
			return user;
		} catch (Exception e) {
			throw new NamingException("User doesn't have email.") {
			};
		}
	}

}
