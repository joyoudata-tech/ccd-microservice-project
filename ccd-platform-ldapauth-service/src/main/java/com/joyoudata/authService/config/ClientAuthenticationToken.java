package com.joyoudata.authService.config;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class ClientAuthenticationToken extends AbstractAuthenticationToken {
	
	private final Object principal;
	
	private final Object credentials;
	
	private final boolean client;
	
	public ClientAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal,
			Object credentials) {
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
