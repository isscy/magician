package cn.redsoft.magician.core.auth.config;

import cn.redsoft.magician.core.auth.constant.Oauth2Property;
import cn.redsoft.magician.core.auth.security.exception.AuthExceptionEntryPoint;
import cn.redsoft.magician.core.auth.security.handler.DefaultAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter {
    @Autowired
    private DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    @Autowired
    private Oauth2Property oauth2Property;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(oauth2Property.unCheckUrlArray()).permitAll()
                .anyRequest().authenticated();
    }

    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint()).accessDeniedHandler(defaultAccessDeniedHandler);;
    }

}
