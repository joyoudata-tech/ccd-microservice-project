package com.joyoudata.authService.service;

import java.util.ArrayList;
import java.util.List;

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
	
	//自己定义的字段查询，不包含权限
	public User findUserWithSAMAccountName(String sAMAccountName) {
		User u = userRepository.findByUsername(sAMAccountName);
		if (u != null) {
			return getUserWithoutRole(u);
		}else{
			return null;
		}		
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteAll() {
		userRepository.deleteAll();
	}
	
	public List<String> findRolesByUserName(User user) {
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

	public List<User> findAllUsers() {
		List<User> users = userRepository.findUsers();
		return users;
	}

	public void deleteUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			userRepository.delete(user);
		}		
	}
	
	private User getUserWithoutRole(User user) {
		User u = new User();
		u.setUsername(user.getUsername());
		u.setFirstName(user.getUsername());
		u.setLastName(user.getLastName());
		u.setFullName(user.getFullName());
		u.setEmail(user.getEmail());
		u.setMemberOf(user.getMemberOf());
		u.setPhone(user.getPhone());
		u.setDateCreated(user.getDateCreated());
		return u;
	}
}
