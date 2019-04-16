package cn.redsoft.magician.core.auth.config;

import cn.redsoft.magician.core.auth.constant.Oauth2Property;
import cn.redsoft.magician.core.auth.service.DefaultClientDetailsService;
import cn.redsoft.magician.core.auth.utils.JwtAccessToken;
import cn.redsoft.magician.core.common.constant.SecurityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 授权服务端配置
 *
 * @author fengfan 20190329
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private WebResponseExceptionTranslator defaultWebResponseExceptionTranslator;

    @Autowired
    //@Qualifier("baseUserDetailsService")
    private UserDetailsService usernameUserDetailsService;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private Oauth2Property oauth2Property;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
                .withClient("web")
                .secret("java-web")
                .scopes("all")
                .authorities("R", "W", "")
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code");*/
        clients.withClientDetails(new DefaultClientDetailsService());
    }


    /*@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }*/
    @Bean
    public TokenStore tokenStore() {

        /*RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(SecurityConstant.PROJECT_PREFIX + SecurityConstant.OAUTH_PREFIX);
        return tokenStore;*/
        return new JwtTokenStore(jwtAccessToken());
        // TODO : 使用redis 存储经过jwt扩充了的token
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(1);
            additionalInfo.put("test", "testing");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints

                .authenticationManager(authenticationManager)
                .userDetailsService(usernameUserDetailsService)
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessToken())
                .exceptionTranslator(defaultWebResponseExceptionTranslator); // 错误处理
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .allowFormAuthenticationForClients();
    }

    @Bean
    JwtAccessTokenConverter jwtAccessToken() {
        LOGGER.info("Initializing JWT key " + oauth2Property.getToken().getSecret());
        JwtAccessTokenConverter tokenConverter = new JwtAccessToken();
        tokenConverter.setSigningKey(oauth2Property.getToken().getSecret());
        return tokenConverter;
    }


}
