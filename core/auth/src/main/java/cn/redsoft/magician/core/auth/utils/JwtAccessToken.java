package cn.redsoft.magician.core.auth.utils;

import cn.redsoft.magician.core.auth.entity.BaseUserDetail;
import cn.redsoft.magician.core.common.constant.SecurityConstant;
import cn.redsoft.magician.core.common.entity.SysRole;
import cn.redsoft.magician.core.common.entity.SysUser;
import cn.redsoft.magician.core.common.utils.JsonUtil;
import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * 自定义JwtAccessToken 添加额外用户信息
 *
 * @author fengfan 20190329
 */
public class JwtAccessToken extends JwtAccessTokenConverter {

    /**
     * 生成token
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);

        // 设置额外用户信息
        /*SysUser baseUser = ((SysUser) authentication.getPrincipal());
        baseUser.setPassword(null);*/
        // 将用户信息添加到token额外信息中
        SysUser sysUser = ((BaseUserDetail) authentication.getPrincipal()).getSysUser();
        sysUser.setPassword("hello world");
        defaultOAuth2AccessToken.getAdditionalInformation().put(SecurityConstant.USER_INFO, sysUser);

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    /**
     * 解析token
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
        return oauth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken, Map<String, ?> map) {
        accessToken.getAdditionalInformation().put(SecurityConstant.USER_INFO, convertUserData(map.get(SecurityConstant.USER_INFO)));

    }

    private SysUser convertUserData(Object map) {
        String json = JsonUtil.deserializer(map);
        SysUser user = JsonUtil.serializable(json, SysUser.class);
        return user;
    }
}
