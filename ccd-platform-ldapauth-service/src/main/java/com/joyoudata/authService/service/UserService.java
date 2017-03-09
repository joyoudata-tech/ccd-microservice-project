package com.joyoudata.authService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.domain.UserRole;
import com.joyoudata.authService.repository.UserRepository;
import com.joyoudata.authService.repository.UserRoleRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public User findUserById(String id) {
		return userRepository.findOne(id);
	}
	
	public User findUserBySAMAccountName(String sAMAccountName) {
		return userRepository.findByUsername(sAMAccountName);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteAll() {
		userRepository.deleteAll();
	}
	
	public List<UserRole> findRolesByUserName(User user) {
		return userRoleRepository.findRoleByUser(user);
	}
	
	public List<UserRole> saveUserRolesWithUserName(String sAMAccountName, List<String> roles) {
		List<UserRole> userRoles = new ArrayList<UserRole>();
		roles.stream().forEach(r ->{
			User user = userRepository.findByUsername(sAMAccountName);
			if (user != null) {
				UserRole userRole = userRoleRepository.save(new UserRole(user, r));
				userRoles.add(userRole);
			}
		});
		return userRoles;
	}
	
	
	public void deleteUserRoleWithUserName(String sAMAccountName, String role) {
		User user = userRepository.findByUsername(sAMAccountName);
		if (user != null) {
			userRoleRepository.delete(new UserRole(user, role));
		}		
	}
	
	public void deleteAllRolesWithUserName(User user) {
		userRoleRepository.deleteRoleByUser(user);
	}
}
