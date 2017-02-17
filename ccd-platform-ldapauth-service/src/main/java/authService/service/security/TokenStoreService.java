package authService.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import authService.domain.OAuth2AuthenticationAccessToken;
import authService.domain.OAuth2AuthenticationRefreshToken;
import authService.repository.OAuth2AccessTokenRepository;
import authService.repository.OAuth2RefreshTokenRepository;

public class TokenStoreService implements TokenStore {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private OAuth2AccessTokenRepository tokenRepository;
	
	@Autowired
	private OAuth2RefreshTokenRepository refreshTokenRepository;

	private final AuthenticationKeyGenerator keyGenerator = new DefaultAuthenticationKeyGenerator();
	
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tokenId").is(token));
		OAuth2AuthenticationAccessToken accessToken = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
		if (accessToken != null) {
			return accessToken.getAuthentication();
		}
		return null;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		OAuth2AuthenticationAccessToken accessToken = new OAuth2AuthenticationAccessToken(
				token,authentication,keyGenerator.extractKey(authentication));
		mongoTemplate.save(accessToken);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tokenId").is(tokenValue));
		OAuth2AuthenticationAccessToken accessToken = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
		if (accessToken == null) {
			throw new InvalidTokenException("Access token is invalid.");
		}
		return accessToken.getOAuth2AccessToken();
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tokenId").is(token.getValue()));
		OAuth2AuthenticationAccessToken accessToken = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
		if (accessToken != null) {
			tokenRepository.delete(accessToken);
		}
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		refreshTokenRepository.save(new OAuth2AuthenticationRefreshToken(refreshToken, authentication));		
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tokenId").is(tokenValue));
		OAuth2AuthenticationRefreshToken refreshToken = mongoTemplate.findOne(query, OAuth2AuthenticationRefreshToken.class, "oauth2_refresh_token");
		if (refreshToken != null) {
			return refreshToken.getoAuth2RefreshToken();
		}
		return null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tokenId").is(token.getValue()));
		OAuth2AuthenticationRefreshToken refreshToken = mongoTemplate.findOne(query, OAuth2AuthenticationRefreshToken.class, "oauth2_refresh_token");
		if (refreshToken == null) {
			throw new InvalidTokenException("Access token is invalid.");
		}
		return refreshToken.getAuthentication();
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		Query query = new Query();
		query.addCriteria(Criteria.where("tokenId").is(token.getValue()));
		OAuth2AuthenticationRefreshToken refreshToken = mongoTemplate.findOne(query, OAuth2AuthenticationRefreshToken.class, "oauth2_refresh_token");
		if (refreshToken != null) {
			refreshTokenRepository.delete(refreshToken);
		}		
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		Query query = new Query();
		query.addCriteria(Criteria.where("refreshToken").is(refreshToken.getValue()));
		OAuth2AuthenticationAccessToken accessToken = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
		if (accessToken != null) {
			tokenRepository.delete(accessToken);
		}
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		String authenticationId = keyGenerator.extractKey(authentication);
		if (authenticationId == null) {
			return null;
		}
		Query query = new Query();
		query.addCriteria(Criteria.where("authenticationId").is(authenticationId));
		OAuth2AuthenticationAccessToken accessToken = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
		if (accessToken != null) {
			return accessToken.getOAuth2AccessToken();
		}
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("clientId").is(clientId));
		query.addCriteria(Criteria.where("userName").is(userName));
		List<OAuth2AuthenticationAccessToken> accessTokens = mongoTemplate.find(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
		List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
		accessTokens.stream().forEach(t -> {
			tokens.add(t.getOAuth2AccessToken());
		});
		return tokens;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("clientId").is(clientId));
		List<OAuth2AuthenticationAccessToken> accessTokens = mongoTemplate.find(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
		List<OAuth2AccessToken> tokens = new ArrayList<OAuth2AccessToken>();
		accessTokens.stream().forEach(t -> {
			tokens.add(t.getOAuth2AccessToken());
		});
		return tokens;
	}
}
