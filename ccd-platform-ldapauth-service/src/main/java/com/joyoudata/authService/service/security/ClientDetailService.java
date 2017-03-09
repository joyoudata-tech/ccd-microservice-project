package com.joyoudata.authService.service.security;

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
import org.springframework.stereotype.Service;

import com.joyoudata.authService.domain.ClientDetail;
import com.joyoudata.authService.repository.ClientDetailRepository;
import com.joyoudata.authService.utils.StringConvertUtils;

@Service
public class ClientDetailService implements ClientDetailsService, ClientRegistrationService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ClientDetailRepository clientDetailRepository;

	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		ClientDetail client = getJDBCClientDetailsFromClient(clientDetails);
		clientDetailRepository.save(client);
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		ClientDetail clientDetail = clientDetailRepository.findByClientId(clientDetails.getClientId());
		if (clientDetail == null) {
			throw new ClientRegistrationException("Client not found with id '" + clientDetails.getClientId() + "'");
		}
		ClientDetail client = getJDBCClientDetailsFromClient(clientDetails);
		clientDetailRepository.save(client);
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		ClientDetail clientDetail = clientDetailRepository.findByClientId(clientId);
		if (clientDetail == null) {
			throw new ClientRegistrationException("Client not found with id '" + clientId + "'");
		}
		clientDetail.setClientSecret(passwordEncoder.encode(secret));
		clientDetailRepository.save(clientDetail);
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		ClientDetail clientDetail = clientDetailRepository.findByClientId(clientId);
		if (clientDetail == null) {
			throw new ClientRegistrationException("Client not found with id '" + clientId + "'");
		}
		clientDetailRepository.delete(clientDetail);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List listClientDetails() {
		List clientDetails = clientDetailRepository.findAll();		
		return getClientsFromJDBCClientDetails(clientDetails);
	}
	
	public ClientDetail save(ClientDetail authClient) {
        return clientDetailRepository.save(authClient);
    }

    public void deleteAll() {
    	clientDetailRepository.deleteAll();
    }
	
	private List<BaseClientDetails> getClientsFromJDBCClientDetails(List<ClientDetail> clientDetails) {
        List<BaseClientDetails> bcds = new LinkedList<>();
        if (clientDetails != null && !clientDetails.isEmpty()) {
            clientDetails.stream().forEach(mdbcd -> {
                bcds.add(getClientFromJDBCClientDetails(mdbcd));
            });
        }
        return bcds;
    }

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetail client = clientDetailRepository.findByClientId(clientId);
		if (client == null) {
			throw new ClientRegistrationException("Client not found with id '" + clientId + "'");
		}
		return getClientFromJDBCClientDetails(client);
	}
	
	private BaseClientDetails getClientFromJDBCClientDetails(ClientDetail clientDetails) {
        BaseClientDetails bc = new BaseClientDetails();
        bc.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValidity());
        bc.setAuthorizedGrantTypes(StringConvertUtils.stringToSet(clientDetails.getAuthorizedGrantTypes()));
        bc.setClientId(clientDetails.getClientId());
        bc.setClientSecret(clientDetails.getClientSecret());
        bc.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValidity());
        bc.setRegisteredRedirectUri(StringConvertUtils.stringToSet(clientDetails.getWebServerRedirectUri()));
        bc.setResourceIds(StringConvertUtils.stringToSet(clientDetails.getResourceIds()));
        bc.setScope(StringConvertUtils.stringToSet(clientDetails.getScope()));
        return bc;
    }

    private ClientDetail getJDBCClientDetailsFromClient(ClientDetails cd) {
        ClientDetail clientDetails = new ClientDetail();
        clientDetails.setAccessTokenValidity(cd.getAccessTokenValiditySeconds());
        clientDetails.setAuthorizedGrantTypes(cd.getAuthorizedGrantTypes().toString());
        clientDetails.setClientId(cd.getClientId());
        clientDetails.setClientSecret(cd.getClientSecret());
        clientDetails.setRefreshTokenValidity(cd.getRefreshTokenValiditySeconds());
        clientDetails.setWebServerRedirectUri(cd.getRegisteredRedirectUri().toString());
        clientDetails.setResourceIds(cd.getResourceIds().toString());
        clientDetails.setScope(cd.getScope().toString());
        clientDetails.setScoped(cd.isScoped());
        clientDetails.setSecretRequired(cd.isSecretRequired());
        clientDetails.setClientId(cd.getClientId());
        return clientDetails;
    }

}
