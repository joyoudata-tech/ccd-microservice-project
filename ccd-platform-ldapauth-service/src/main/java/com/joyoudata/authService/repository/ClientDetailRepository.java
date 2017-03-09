package com.joyoudata.authService.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.joyoudata.authService.domain.ClientDetail;

public interface ClientDetailRepository extends PagingAndSortingRepository<ClientDetail, Serializable>{

	ClientDetail findByClientId(String clientId);
	
	List<ClientDetail> findAll();

}
