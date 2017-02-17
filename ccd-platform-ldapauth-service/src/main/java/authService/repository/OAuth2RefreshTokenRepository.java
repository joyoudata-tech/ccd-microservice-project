package authService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import authService.domain.OAuth2AuthenticationRefreshToken;

public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuth2AuthenticationRefreshToken, String>{

}
