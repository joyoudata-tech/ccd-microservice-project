package authService.service.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import authService.domain.UserDetail;

/**
 * 普通用户认证权限提供服务 组件
 * */
@Component
public class UserAuthProviderService implements AuthenticationProvider{
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserAuthConfigService authConfigService;

	@SuppressWarnings("serial")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetail user = authConfigService.getUser(email);
		if (user != null) {
			//赋予权限
			if (encoder.matches(password, user.getUserPassword())) {
				List<GrantedAuthority> rights = authConfigService.getRight(user);
				return authConfigService.signRolesInUser(user, rights);
			}
			throw new AuthenticationException("User: " + email + " passoword is not correct."){
			};
		}
		throw new AuthenticationException("Can't find email '" + email + "' for user."){		
		};
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	
}
