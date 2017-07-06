package com.joyoudata.authService.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.domain.UserRole;
import com.joyoudata.authService.service.UserService;

@RestController
public class UserController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public List<User> getUsers() {
		List<User> users = userService.findAllUsers();
		return users;
	}
	
	@RequestMapping(value="/users/{username}/summary",method=RequestMethod.GET)
	public User getUserWithUsername(@PathVariable String username) {
		return userService.findUserWithSAMAccountName(username);	
	}
	
	@RequestMapping(value="/users/{username}",method=RequestMethod.PUT)
	public User modifyUserByUsername(@PathVariable String username,@RequestBody User user) {
		User oldUser = userService.findUserBySAMAccountName(username);
		if (oldUser != null) {
			return userService.saveUser(user);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/users/{username}",method=RequestMethod.DELETE)
	public void deleteUserByUsername(@PathVariable String username) {
		userService.deleteUserByUsername(username);
	}
	
	@RequestMapping(value="/users/{username}/roles",method=RequestMethod.GET)
	public List<String> getUserRoles(@PathVariable String username) {
		User user = userService.findUserBySAMAccountName(username);
		if (user != null) {
			return userService.findRolesByUserName(user);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/users/{username}/roles",method=RequestMethod.POST)
	public List<UserRole> addRoleByUsername(@PathVariable String username,@RequestBody String role){
		User user = userService.findUserBySAMAccountName(username);
		if (user != null) {
			List<UserRole> userRoles = userService.saveUserRolesWithUserName(username, Arrays.asList(role));
			return userRoles;
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/users/{username}/roles/{rolename}",method=RequestMethod.DELETE)
	public void deleteRoleByUsername(@PathVariable String username, @PathVariable String rolename) {
		User user = userService.findUserBySAMAccountName(username);
		if (user != null) {
			userService.deleteUserRoleWithUserName(username, rolename);
		}
	}
	
}
