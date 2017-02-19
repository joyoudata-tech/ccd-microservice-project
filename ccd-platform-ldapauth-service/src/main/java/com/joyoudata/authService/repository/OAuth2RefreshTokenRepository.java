package com.joyoudata.authService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.joyoudata.authService.domain.OAuth2AuthenticationRefreshToken;

public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuth2AuthenticationRefreshToken, String>{

}
