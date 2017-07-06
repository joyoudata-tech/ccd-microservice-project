package com.joyoudata.authService.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.joyoudata.authService.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Serializable>{
	
	@Query("select u from User u where u.username = ?1")
	public List<User> findWithUsername(String username);
	
	public User findByUsername(String username);
	
	@Query("select u.username,u.email,u.memberOf from User u")
	public List<User> findUsers();
}
