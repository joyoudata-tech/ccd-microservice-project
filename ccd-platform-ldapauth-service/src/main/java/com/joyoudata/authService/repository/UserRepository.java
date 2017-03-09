package com.joyoudata.authService.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.joyoudata.authService.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Serializable>{

	public User findByUsername(String username);
	
	public List<User> findAll();
}
