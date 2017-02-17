package authService.service.security;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import authService.domain.ClientDetail;
import authService.repository.ClientDetailRepository;

public class ClientDetailService implements ClientDetailsService, ClientRegistrationService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ClientDetailRepository clientDetailRepository;

	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		ClientDetail clientDetail = getMongoDBClientDetailsFromClient(clientDetails);
		clientDetailRepository.save(clientDetail);
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		ClientDetail clientDetail = clientDetailRepository.findByClientId(clientDetails.getClientId());
		if (clientDetail == null) {
			throw new NoSuchClientException("No Client found for id: " + clientDetails.getClientId());
		}
		clientDetailRepository.save(getMongoDBClientDetailsFromClient(clientDetails));
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		ClientDetail clientDetail = clientDetailRepository.findByClientId(clientId);
		if (clientDetail == null) {
			throw new NoSuchClientException("No Client found for id: " + clientId);
		}
		//密码需要被转换
		clientDetail.setClientSecret(passwordEncoder.encode(secret));
		clientDetailRepository.save(clientDetail);
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		ClientDetail clientDetail = clientDetailRepository.findByClientId(clientId);
		if (clientDetail == null) {
			throw new NoSuchClientException("No Client found for id: " + clientId);
		}
		clientDetailRepository.delete(clientDetail);
	}

	@Override
	public List listClientDetails() {
		List<ClientDetail> clients = clientDetailRepository.findAll();
		return getClientsFromMongoDBClientDetails(clients);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetail clientDetail = clientDetailRepository.findByClientId(clientId);
		if (clientDetail == null) {
			throw new NoSuchClientException("No Client found for id: " + clientId);
		}
		return getClientFromMongoDBClientDetails(clientDetail);
	}
	
	//新增删除所有
	public void deleteAll() {
		clientDetailRepository.deleteAll();
	}
	
	//新增按照原有clientDetail对象存储
	public ClientDetail save(ClientDetail clientDetail) {
		return clientDetailRepository.save(clientDetail);
	}
	
	private ClientDetail getMongoDBClientDetailsFromClient(ClientDetails cd) {
		ClientDetail client = new ClientDetail();
		client.setAccessTokenValiditySeconds(cd.getAccessTokenValiditySeconds());
		client.setAdditionalInformation(cd.getAdditionalInformation());
		client.setAuthorizedGrantTypes(cd.getAuthorizedGrantTypes());
		client.setClientId(cd.getClientId());
		client.setClientSecret(cd.getClientSecret());
		client.setRefreshTokenValiditySeconds(cd.getRefreshTokenValiditySeconds());
		client.setRegisteredRedirectUri(cd.getRegisteredRedirectUri());
		client.setResourceIds(cd.getResourceIds());
		client.setScope(cd.getScope());
		client.setId(cd.getClientId());
		client.setSecretRequired(cd.isSecretRequired());
		client.setScoped(cd.isScoped());
		return client;
	}
	
	private BaseClientDetails getClientFromMongoDBClientDetails(ClientDetail clientDetails) {
        BaseClientDetails bc = new BaseClientDetails();
        bc.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds());
        bc.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes());
        bc.setClientId(clientDetails.getClientId());
        bc.setClientSecret(clientDetails.getClientSecret());
        bc.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
        bc.setRegisteredRedirectUri(clientDetails.getRegisteredRedirectUri());
        bc.setResourceIds(clientDetails.getResourceIds());
        bc.setScope(clientDetails.getScope());
        return bc;
    }
	
	private List<BaseClientDetails> getClientsFromMongoDBClientDetails(List<ClientDetail> clientDetails) {
        List<BaseClientDetails> bcds = new LinkedList<>();
        if (clientDetails != null && !clientDetails.isEmpty()) {
            clientDetails.stream().forEach(mdbcd -> {
                bcds.add(getClientFromMongoDBClientDetails(mdbcd));
            });
        }
        return bcds;
    }

}
