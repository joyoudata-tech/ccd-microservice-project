package com.joyoudata.authService.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  
@ConfigurationProperties(prefix="oauth2_server")
public class Oauth2ServerProperty {
	
	private List<Map<String,Object>> clients = new ArrayList<>();
	
	private List<Map<String,String>> users = new ArrayList<>();

	public List<Map<String, Object>> getClients() {
		return clients;
	}

	public void setClients(List<Map<String, Object>> clients) {
		this.clients = clients;
	}

	public List<Map<String, String>> getUsers() {
		return users;
	}

	public void setUsers(List<Map<String, String>> users) {
		this.users = users;
	}

}
