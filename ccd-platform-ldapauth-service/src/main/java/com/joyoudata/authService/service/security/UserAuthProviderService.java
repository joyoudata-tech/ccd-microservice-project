package com.joyoudata.authService.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.joyoudata.authService.domain.User;

@Component
public class UserAuthProviderService implements AuthenticationProvider {
	
	@Autowired
    private UserAuthConfigService authConfigService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	@SuppressWarnings("serial")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = authConfigService.getUser(email);
        if (null != user) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                List<GrantedAuthority> roleAuthority = authConfigService.getRights(user);
                return authConfigService.signInUser(user, roleAuthority);
            }
            throw new AuthenticationException("Password for '" + email + "' not correct.") {
            };
        }
        throw new AuthenticationException("Could not find user with name '" + email + "'") {
        };
	}

	@Override
	public boolean supports(Class<?> type) {
		return type.equals(UsernamePasswordAuthenticationToken.class);
	}

}
