package com.joyoudata.authService.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.joyoudata.authService.domain.ClientDetail;

public interface ClientDetailRepository extends MongoRepository<ClientDetail, Serializable>{
	
	public ClientDetail findByClientId(String ClientId);
}
