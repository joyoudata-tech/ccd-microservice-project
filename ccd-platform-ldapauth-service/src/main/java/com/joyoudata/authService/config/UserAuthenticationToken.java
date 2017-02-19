package com.joyoudata.authService.config;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class UserAuthenticationToken extends AbstractAuthenticationToken {
	
	private final Object principal;
	
	private final Object credentials;
	
	private final boolean client;

	public UserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.client = false;
		super.setAuthenticated(true);
	}
	
	public boolean isClient() {
		return client;
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
