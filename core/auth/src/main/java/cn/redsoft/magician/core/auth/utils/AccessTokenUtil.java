package cn.redsoft.magician.core.auth.utils;

import cn.redsoft.magician.core.common.constant.RedisConstant;
import cn.redsoft.magician.core.common.constant.SecurityConstant;
import cn.redsoft.magician.core.common.entity.SysMenu;
import cn.redsoft.magician.core.common.entity.SysRole;
import cn.redsoft.magician.core.common.entity.SysUser;
import cn.redsoft.magician.core.common.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccessTokenUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccessTokenUtil.class);

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private HttpServletRequest request;

    //@Autowired TODO : 暂时找不到注入的bean
    private TokenExtractor tokenExtractor;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 从token获取用户信息
     *
     * @return
     */
    public SysUser getUserInfo() {
        return (SysUser) getAccessToken().getAdditionalInformation().get(SecurityConstant.USER_INFO);
    }

    public List<SysRole> getRoleInfo() {
        String userId = getUserInfo().getId();
        List<SysRole> roles = (ArrayList<SysRole>) redisUtil.hget(RedisConstant.KEY_USER_ROLES, userId);
        if (roles == null || roles.size() == 0) {
            LOGGER.error("用户： " + userId + " 从Redis中获取角色为空");
            return new ArrayList<>();
        }
        return roles;
    }

    public List<SysMenu> getMenuInfo() {
        String userId = getUserInfo().getId();
        List<SysMenu> roles = (ArrayList<SysMenu>) redisUtil.hget(RedisConstant.KEY_USER_ROLES, userId);
        return roles;
    }

    private OAuth2AccessToken getAccessToken() throws AccessDeniedException {
        OAuth2AccessToken token;
        Authentication a = tokenExtractor.extract(request); // 抽取token
        try {
            token = tokenStore.readAccessToken((String) a.getPrincipal());// 调用JwtAccessTokenConverter的extractAccessToken方法解析token
        } catch (Exception e) {
            throw new AccessDeniedException("AccessToken Not Found 错误啊");
        }
        return token;
    }
}
