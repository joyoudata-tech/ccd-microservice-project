package com.joyoudata.authService.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.domain.UserRole;
@Transactional
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Serializable> {
	
	List<UserRole> findRoleByUser(User user);
	
	void deleteRoleByUser(User user);
	
}
