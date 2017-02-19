package com.joyoudata.authService.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import com.joyoudata.authService.domain.UserDetail;
import com.joyoudata.authService.service.security.ClientDetailService;
import com.joyoudata.authService.service.security.UserAuthConfigService;
import com.mongodb.DBObject;

@Configuration
@Component
public class CustomMongoDBConvertor implements Converter<DBObject, OAuth2Authentication>{
	
	//关于mongoTemplate 无法注入的问题：https://github.com/malike/sso-auth/issues/1
	//利用applicationContext手动将操作mongo的两个bean获取到 ，然后再操作，
	//这里主要是mongoRepository涉及到了mongoTemplate在之前已经被注入进容器了，这里只需要依赖查找后再操作。
	@Autowired
	private ApplicationContext applicationContext;
	
	private UserAuthConfigService authConfigService;
	
	private ClientDetailService clientDetailService;

	@Override
	public OAuth2Authentication convert(DBObject source) {
		authConfigService = (UserAuthConfigService)(applicationContext.getBean(UserAuthConfigService.class));
		clientDetailService = (ClientDetailService)(applicationContext.getBean(ClientDetailService.class));
		DBObject storedRequest = (DBObject) source.get("storedRequest");
		//构建OAuth2Request
		OAuth2Request oAuth2Request = new OAuth2Request((Map<String, String>)storedRequest.get("requestParameters"), 
				(String)storedRequest.get("clientId"), 
				null, 
				true, 
				new HashSet((List)storedRequest.get("scope")), 
				null, 
				null, 
				null, 
				null);
		//构建OAuth2Authentication对象
		DBObject userAuthorization = (DBObject) source.get("userAuthentication");
		if (userAuthorization != null) {//如果不是空，则说明是一个user认证
			//构建UserDetail对象
			Object principal = userAuthorization.get("principal");
			UserDetail user = null;
			if (principal != null && (principal instanceof String)) {
				user = authConfigService.getUser((String)principal);
			}else if (principal != null) {
				DBObject principalDBO = (DBObject)principal;
				String email = (String)principalDBO.get("username");
				user = authConfigService.getUser(email);
			}
			if (user == null) {
				return null;
			}
			//构建authorization对象
			Authentication userAuthentication = new UserAuthenticationToken(user.getEmail(), 
					(String) userAuthorization.get("credentials"), 
					authConfigService.getRight(user));
			OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
            return authentication;
		} else {//为空，则说明是client认证方式
			String clientId = (String)storedRequest.get("clientId");
			ClientDetails client = null;
			if (clientId != null) {
				client = clientDetailService.loadClientByClientId(clientId);
			}
			if (client == null) {
				return null;
			}
			//构建authorization对象
			Authentication clientAuthentication = new ClientAuthenticationToken(client.getAuthorities(),null,client.getClientId());
			return new OAuth2Authentication(oAuth2Request, clientAuthentication);		
		}
	}	
	
	@Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(this);
        return new CustomConversions(converterList);
    }
}
