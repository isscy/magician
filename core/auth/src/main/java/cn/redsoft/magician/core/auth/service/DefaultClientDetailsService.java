package cn.redsoft.magician.core.auth.service;

import cn.redsoft.magician.core.auth.mapper.SysOauthClientDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * 获取client oauth2的客户信息
 *
 * @author ff at 20190118
 */
@Service
public class DefaultClientDetailsService implements ClientDetailsService {
    @Autowired
    private SysOauthClientDetailsMapper sysOauthClientDetailsMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        ClientDetails clientDetails = sysOauthClientDetailsMapper.getByClientId(clientId);
        if (clientDetails == null)
            throw new ClientRegistrationException("client不存在");
        return clientDetails;
    }
}
