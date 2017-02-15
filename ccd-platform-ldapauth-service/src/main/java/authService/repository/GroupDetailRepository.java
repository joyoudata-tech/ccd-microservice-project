package authService.repository;

import java.util.Collection;

import javax.naming.Name;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.ldap.repository.Query;
import org.springframework.data.repository.query.Param;

import authService.domain.GroupDetail;

public interface GroupDetailRepository extends LdapRepository<GroupDetail>{
	public final static String USER_GROUP = "ROLE_USER";
	
	GroupDetail findByName(@Param("groupName") String groupName);
	
	@Query("(member={0})")
	Collection<GroupDetail> findByMembers(Name member);
}
