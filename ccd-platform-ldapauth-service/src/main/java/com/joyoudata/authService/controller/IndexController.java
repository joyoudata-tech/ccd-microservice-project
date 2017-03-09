package com.joyoudata.authService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.repository.UserRepository;

@RestController
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "Welcome to the home page!";
    }
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
    public List<User> getUsers() {		
        return userRepository.findAll();
    }

}
