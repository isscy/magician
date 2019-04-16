package cn.redsoft.magician.core.auth.config;

import cn.redsoft.magician.core.auth.constant.Oauth2Property;
import cn.redsoft.magician.core.auth.security.AccessDecisionManagerImpl;
import cn.redsoft.magician.core.auth.security.filter.AuthorizationSecurityInterceptor;
import cn.redsoft.magician.core.auth.security.FilterInvocationSecurityMetadataSourceImpl;
import cn.redsoft.magician.core.auth.security.filter.DefaultLoginAuthenticationFilter;
import cn.redsoft.magician.core.auth.security.provider.PhoneAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final static Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private Oauth2Property oauth2Property;
    @Autowired
    private UserDetailsService baseUserDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //.exceptionHandling()  TODO -> 错误处理 以后要用一下
                //.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                //.and()
                //AuthorizationSecurityInterceptor
                .authorizeRequests()//.accessDecisionManager(accessDecisionManager())
                .antMatchers("/**").authenticated()
                .and()
                .addFilterBefore(authorizationSecurityInterceptor(), FilterSecurityInterceptor.class);
                //.addFilterBefore(defaultLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//                .and()
//                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /*@Bean
    public PhoneAuthenticationProvider phoneAuthenticationProvider(){
        PhoneAuthenticationProvider provider = new PhoneAuthenticationProvider();
        provider.setUserDetailsService(phoneUserDetailService);// 设置userDetailsService
        provider.setHideUserNotFoundExceptions(false);// 禁止隐藏用户未找到异常
        return provider;
    }*/


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(baseUserDetailsService);// 设置userDetailsService
        provider.setHideUserNotFoundExceptions(false);// 禁止隐藏用户未找到异常
        provider.setPasswordEncoder(passwordEncoder());// 使用BCrypt进行密码的hash
        return provider;
    }

    @Bean//(name = "accessDecisionManager")
    public AccessDecisionManager accessDecisionManager() {
        return new AccessDecisionManagerImpl();
    }

    @Bean
    FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new FilterInvocationSecurityMetadataSourceImpl();
    }

    //@DependsOn({"accessDecisionManager", "securityMetadataSource"})
    @Bean(name = "authorizationSecurityInterceptor")
    AuthorizationSecurityInterceptor authorizationSecurityInterceptor() {
        AuthorizationSecurityInterceptor authorizationSecurityInterceptor = new AuthorizationSecurityInterceptor();
        authorizationSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource());
        authorizationSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        return authorizationSecurityInterceptor;
    }

    /*@Bean
    public DefaultLoginAuthenticationFilter defaultLoginAuthenticationFilter() {
        DefaultLoginAuthenticationFilter filter = new DefaultLoginAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
       *//* filter.setAuthenticationSuccessHandler(new MyLoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));*//*
        return filter;
    }*/

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(oauth2Property.unCheckUrlArray());
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
