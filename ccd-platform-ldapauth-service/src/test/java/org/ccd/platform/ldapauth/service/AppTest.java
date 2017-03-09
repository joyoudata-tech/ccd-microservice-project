package org.ccd.platform.ldapauth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

/**
 * Unit test for simple App.
 */
public class AppTest
{
	
//	@org.junit.Test
//    public void testAuthentication() throws Exception {
//        AuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider("joyoudata.com",
//                "ldap://jdc.joyoudata.com:389");
//        
//        Authentication userToken = new UsernamePasswordAuthenticationToken("shaopeng.yuan@joyoudata.com", "a12345678A");
//        AuthenticationManager manager = new ProviderManager(Arrays.asList(provider));
//        manager.authenticate(userToken);
//    }
	
	@Test
	public void testList() {
		String string = new HashSet<>(Arrays.asList("rest_api","jojo","dfd")).toString();
		String[] split = string.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
		List<String> list = Arrays.asList(split);
		System.out.println(list);
		System.out.println(string.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", ""));
		//System.out.println(Arrays.asList(string.replace(oldChar, newChar)));
		
	}
}
