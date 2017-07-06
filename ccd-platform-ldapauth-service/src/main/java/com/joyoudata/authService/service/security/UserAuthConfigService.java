package com.joyoudata.authService.service.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.service.UserService;

@Service
public class UserAuthConfigService implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	public User getUser(String username) {
		return userService.findUserBySAMAccountName(username);
	}
	
	//获得用户的角色权限
	public List<GrantedAuthority> getRights(User user) {
		List<GrantedAuthority> grantedAuthoritys = new LinkedList<>();
		List<String> userRoles = userService.findRolesByUserName(user);
		if (userRoles != null && !userRoles.isEmpty()) {
			userRoles.stream().forEach(r -> {
				grantedAuthoritys.add(new SimpleGrantedAuthority(r));
			});			
		}
		System.out.println("find role: " + userRoles);
		return grantedAuthoritys;
	}
	
	//分配用户角色 用户密码是userId
	public Authentication signInUser(User user, List<GrantedAuthority> roles) {
		UserDetails springSecurityUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getEmail(), roles);
		Authentication authentication = new UsernamePasswordAuthenticationToken(springSecurityUser, user.getEmail(), roles);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserBySAMAccountName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s not found!", user));
		}
		return new UserDetails() {
			private static final long serialVersionUID = 1636806427242197757L;

			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return user.getUsername();
			}
			
			@Override
			public String getPassword() {
				return user.getPassword();
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return getRights(user);
			}
		};
	}
	
}
