package authService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import authService.domain.UserDetail;
import authService.repository.UserRepository;

@Service
public class UserDetailService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserDetail findUserById(String id) {
		return userRepository.findOne(id);
	}
	
	public UserDetail findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public UserDetail saveUser(UserDetail user) {
		return userRepository.save(user);
	}
	
	public void deleteAll() {
		userRepository.deleteAll();
	}

}
