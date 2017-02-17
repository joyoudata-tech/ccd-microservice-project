package authService.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import authService.domain.UserDetail;
import authService.service.UserDetailService;

/**
 * 用户角色权限的分配 
 */
@Service
public class UserAuthConfigService {
	
	@Autowired
	private UserDetailService userDetailService;
	
	public UserDetail getUser(String email) {
		return userDetailService.findUserByEmail(email);
	}
	
	//获得用户的角色权限
	public List<GrantedAuthority> getRight(UserDetail user) {
		List<GrantedAuthority> grantedAuthoritys = new ArrayList<>();
		List<String> rights = user.getRights();
		if (rights != null && !rights.isEmpty()) {
			rights.stream().forEach(r -> {
				grantedAuthoritys.add(new SimpleGrantedAuthority(r));
			});;
			
		}
		return grantedAuthoritys;
	}
	
	//分配用户角色 用户密码是userId
	public Authentication signRolesInUser(UserDetail user, List<GrantedAuthority> roles) {
		//spring security自带的用户
		UserDetails customUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getId(), roles);
		//spring security认证token
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUser, user.getId(), roles);
		//SecurityContext 对authenticationToken进行上下文包装
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		return authenticationToken;
	}
	
}
