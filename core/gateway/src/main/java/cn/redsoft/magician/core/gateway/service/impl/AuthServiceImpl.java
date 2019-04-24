package cn.redsoft.magician.core.gateway.service.impl;

import cn.redsoft.magician.core.common.constant.RedisConstant;
import cn.redsoft.magician.core.common.entity.R;
import cn.redsoft.magician.core.common.utils.RedisUtil;
import cn.redsoft.magician.core.gateway.provider.AuthProvider;
import cn.redsoft.magician.core.gateway.service.AuthService;
//import io.jsonwebtoken.impl.crypto.MacSigner;
import io.jsonwebtoken.impl.crypto.MacSigner;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    //@Value("${spring.cloud.gateway}")
    private final GatewayProperties gatewayConfig;
    private final RedisUtil redisUtil;
    private final AuthProvider authProvider;
    private AntPathMatcher matcher = new AntPathMatcher();

    private MacSigner signer;
    private String signingKey = "s";


    @Override
    public R authenticate(String authentication, String url, String method) {
        return null;
    }

    @Override
    public boolean ignoreAuthentication(String handleServer, String url) {
        Map<String, List<String>> noPermissionRequiredUrlMap = new HashMap<>();
        // TODO 有时间需要再优化
        if (redisUtil.hasKey(RedisConstant.KEY_UNCHECK_URL)) {
            // TODO 统一处理缓存 抽出处理缓存公共方法
            Map<String, Object> map = redisUtil.hmget(RedisConstant.KEY_UNCHECK_URL);
            noPermissionRequiredUrlMap = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> ((List<String>) e.getValue())));
        } else {
            R r = authProvider.noPermissionRequiredUrl();
            if (!r.isSuccess()) {
                LOGGER.error("获取noPermissionRequiredUrl错误 ! " + r.getMessage());
                return false;
            }
            noPermissionRequiredUrlMap = (Map<String, List<String>>) r.getData();
            //TODO 存到Redis
        }

        List<String> urlList = noPermissionRequiredUrlMap.get(handleServer);
        if (urlList == null)
            return false;
        return urlList.stream().anyMatch(u -> matcher.match(u, url));
    }

    @Override
    public boolean hasPermission(R authResult) {
        return authResult.isSuccess() && (boolean) authResult.getData();
    }

    @Override
    public boolean hasPermission(String authentication, String url, String method) {
        //token是否有效
       /* if (invalidJwtAccessToken(authentication)) {
            return Boolean.FALSE;
        }*/
        //从认证服务获取是否有权限
        R r = authProvider.auth(authentication, url, method);
        if (r.isSuccess()){
            Map<String, Object> map = (Map<String, Object>)r.getData();
            return (Boolean) map.get("success");
        }
        return false;
        //return hasPermission(authenticate(authentication, url, method));
    }


    /*public boolean invalidJwtAccessToken(String authentication) {
        //TODO  获取 signingKey
        signer = Optional.ofNullable(signer).orElse(new MacSigner(signingKey));
        //是否无效true表示无效
        boolean invalid = Boolean.TRUE;
        try {
            Jwt jwt = getJwt(authentication);
            jwt.verifySignature(verifier);
            invalid = Boolean.FALSE;
        } catch (InvalidSignatureException | IllegalArgumentException ex) {
            log.warn("user token has expired or signature error ");
        }
        return invalid;
    }*/

}
