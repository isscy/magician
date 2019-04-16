package cn.redsoft.magician.core.auth.security;

import cn.redsoft.magician.core.auth.constant.Oauth2Property;
import cn.redsoft.magician.core.auth.mapper.SysMenuMapper;
import cn.redsoft.magician.core.common.constant.RedisConstant;
import cn.redsoft.magician.core.common.entity.SysMenu;
import cn.redsoft.magician.core.common.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 路径所需的角色集合
 *
 * @author fengfan 20190408
 */
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    private final static Logger LOGGER = LoggerFactory.getLogger(FilterInvocationSecurityMetadataSourceImpl.class);
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private SysMenuMapper sysMenuMapper;

    private Map<String, Collection<ConfigAttribute>> urlRoleMap = new LinkedHashMap<>();

    @Autowired
    private RedisUtil redisUtil;


    /**
     * @param object 当前用户访问的受保护的资源
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        LOGGER.info("-----------------------FilterInvocationSecurityMetadataSourceImpl.getAttributes-----------------------");
        // TODO : 去掉参数 ？
        String url = ((FilterInvocation) object).getRequestUrl();


        return getOneUrlRoles(url);
        //return new HashSet<>();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {

        System.err.println("+++++++++++++++++++FilterInvocationSecurityMetadataSourceImpl.getAllConfigAttributes+++++++++++++++++++");


        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private Collection<ConfigAttribute> getOneUrlRoles(String url){// TODO 优化
        Set<String> rdUrls = redisUtil.hkeys("*");
        for (String rd : rdUrls){
            if (antPathMatcher.match(rd, url)){
                String roles = redisUtil.hget(RedisConstant.KEY_URL_ROLES, rd).toString();
                return tranToRoleList(roles);
            }
        }
        SysMenu menu = sysMenuMapper.getOneUrlAndRole(url);
        if (menu != null && !StringUtils.isEmpty(menu.getOfRoles())){
            // TODO 加到redis 里
            return tranToRoleList(menu.getOfRoles());
        }



        return null;
        //return SecurityConfig.createList("ROLE_FULLY");//没有匹配到,默认是要登录才能访问
    }


    private Collection<ConfigAttribute> tranToRoleList(String roleString){
        Collection<ConfigAttribute> configAttributes =  SecurityConfig.createList (roleString.split(","));
        return configAttributes;
    }


    /**
     * 获取请求去参数后的url
     */
    private String getUrl(FilterInvocation fi) {
        String url = fi.getRequestUrl();
        String expr = "?";
        if (url.contains(expr)) {
            url = url.substring(0, url.indexOf(expr));
        }
        return url;
    }
}
