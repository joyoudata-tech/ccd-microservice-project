package com.joyoudata.authService.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.joyoudata.authService.domain.User;
import com.joyoudata.authService.domain.UserRole;
@Transactional
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Serializable> {
	
	void deleteRoleByUser(User user);

	@Query("select r.role from UserRole r where r.user=?1")
	List<String> findRoleByUser(User user);
	
}
