package authService.repository;

import java.util.List;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import authService.domain.UserDetail;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserDetailRepository extends LdapRepository<UserDetail>{
	
	List<UserDetail> findByFullNameContains(@Param("name")String name);

}
