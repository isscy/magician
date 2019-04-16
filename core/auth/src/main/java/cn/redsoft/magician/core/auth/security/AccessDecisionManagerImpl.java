package cn.redsoft.magician.core.auth.security;

import cn.redsoft.magician.core.auth.constant.Oauth2Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Iterator;

/**
 * 自定义权限判断
 *
 * @author fengfan 20190403
 */
public class AccessDecisionManagerImpl implements AccessDecisionManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccessDecisionManagerImpl.class);
    @Autowired
    private Oauth2Property oauth2Property;
    private AntPathMatcher matcher = new AntPathMatcher();


    /**
     * @param authentication 当前认证通过的用户的认证信息
     * @param object         当前用户访问的受保护资源,如URL
     * @param configAttributes     当前受保护资源需要的角色,权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        String url = getUrl(object);
        LOGGER.warn("自定义权限判断： url= " + url);

        for (String unCkUrl : oauth2Property.unCheckUrlArray()){ // 放过不需要权限的url
            if (matcher.match(unCkUrl, unCkUrl))
                return;
        }

        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while (ite.hasNext()) { // 遍历判断该url所需的角色看用户是否具备
            ConfigAttribute ca = ite.next();
            String needRole = ((org.springframework.security.access.SecurityConfig) ca).getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if(ga.getAuthority().equals(needRole)){
                    //匹配到有对应角色,则允许通过
                    return;
                }
            }
        }
        //该url有配置权限,但是当然登录用户没有匹配到对应权限,则禁止访问
         throw new AccessDeniedException("接口无权访问");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


    private String getUrl(Object o) {
        String url = ((FilterInvocation) o).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            return url.substring(0, firstQuestionMarkIndex);
        }
        return url;
    }

    // TODO 参考 https://github.com/fp2952/spring-cloud-base/blob/master/auth-center/auth-spring-boot-autoconfigure/src/main/java/com/peng/auth/spring/boot/autoconfigure/config/AccessDecisionManagerIml.java


}
