package authService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.mongodb.DBObject;

@Configuration
public class MongoConfig implements Converter<DBObject, OAuth2Authentication>{

	@Override
	public OAuth2Authentication convert(DBObject source) {
		// TODO Auto-generated method stub
		return null;
	}	

}
