package com.joyoudata.authService.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.joyoudata.authService.domain.UserDetail;

public interface UserRepository extends MongoRepository<UserDetail, Serializable>{
	
	public UserDetail findByEmail(String email);
}
