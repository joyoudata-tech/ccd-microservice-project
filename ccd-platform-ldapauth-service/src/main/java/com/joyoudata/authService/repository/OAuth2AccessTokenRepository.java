package com.joyoudata.authService.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.joyoudata.authService.domain.OAuth2AuthenticationAccessToken;

public interface OAuth2AccessTokenRepository extends MongoRepository<OAuth2AuthenticationAccessToken, Serializable>{

}
