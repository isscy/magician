package cn.redsoft.magician.core.auth.mapper;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOauthClientDetailsMapper {
    ClientDetails getByClientId(String clientId);
}
