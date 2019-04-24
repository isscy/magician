package cn.redsoft.magician.core.auth.service;

import cn.redsoft.magician.core.auth.mapper.SysMenuMapper;
import cn.redsoft.magician.core.common.entity.SysMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationService {

    private final SysMenuMapper sysMenuMapper;

    /**
     * 未在资源库中的URL默认标识
     */
    public static final String NONEXISTENT_URL = "NONEXISTENT_URL";
    private AntPathMatcher matcher = new AntPathMatcher();


    /**
     * 系统中所有权限集合
     */
    Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes;




    public Map<String, Object> decide(HttpServletRequest request, String url, String method, String userId) {
        log.debug("正在访问的url是: {}   {}", method, url);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//获取用户认证信息
        /*ConfigAttribute urlConfigAttribute = findConfigAttributesByUrl(request, userId, method, url); //获取此url，method访问对应的权限资源信息
        if (NONEXISTENT_URL.equals(urlConfigAttribute.getAttribute()))
            log.debug("url未在资源池中找到，拒绝访问");*/
        //获取此访问用户所有角色拥有的权限资源
//        Set<SysMenu> userResources = findResourcesByAuthorityRoles(authentication.getAuthorities());
//        //用户拥有权限资源 与 url要求的资源进行对比
//        return isMatch(urlConfigAttribute, userResources);
        boolean rst =  findConfigAttributesByUrl(request, userId, method, url);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", rst);
        return resultMap;
    }

    /**
     * url对应资源与用户拥有资源进行匹配

    public boolean isMatch(ConfigAttribute urlConfigAttribute, Set<Resource> userResources) {
        return userResources.stream().anyMatch(resource -> resource.getCode().equals(urlConfigAttribute.getAttribute()));
    }
     */
    /**
     * 根据url和method查询到对应的权限信息
     */
    public /*ConfigAttribute*/ boolean findConfigAttributesByUrl(HttpServletRequest authRequest, String userId, String method, String url) {
        // TODO 从 Redis中 查询 -->  抽出来一个util 先从Redis中查询没有再db中获取
        /*return this.resourceConfigAttributes.keySet().stream()
                .filter(requestMatcher -> requestMatcher.matches(authRequest))
                .map(requestMatcher -> this.resourceConfigAttributes.get(requestMatcher))
                .peek(urlConfigAttribute -> log.debug("url在资源池中配置：{}", urlConfigAttribute.getAttribute()))
                .findFirst()
                .orElse(new SecurityConfig(NONEXISTENT_URL));*/
        // xxx 这里只是暂时的 需要改的！啊
        List<SysMenu> list = sysMenuMapper.getByUserIdAndMethod(userId, method);
        if (list != null && list.size() > 0){
            for (SysMenu menu : list){
                if (matcher.match(menu.getUrl(), url))
                    return true;

            }
        }

        return false;
    }

    /**
     * 根据用户所被授予的角色，查询到用户所拥有的资源

    private Set<Resource> findResourcesByAuthorityRoles(Collection<? extends GrantedAuthority> authorityRoles) {
        //用户被授予的角色
        log.debug("用户的授权角色集合信息为:{}", authorityRoles);
        String[] authorityRoleCodes = authorityRoles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .toArray(new String[authorityRoles.size()]);
        Set<Resource> resources = resourceService.queryByRoleCodes(authorityRoleCodes);
        if (log.isDebugEnabled()) {
            log.debug("用户被授予角色的资源数量是:{}, 资源集合信息为:{}", resources.size(), resources);
        }
        return resources;
    }*/
}
