package authService.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import authService.domain.OAuth2AuthenticationAccessToken;

public interface OAuth2AccessTokenRepository extends MongoRepository<OAuth2AuthenticationAccessToken, Serializable>{

}
