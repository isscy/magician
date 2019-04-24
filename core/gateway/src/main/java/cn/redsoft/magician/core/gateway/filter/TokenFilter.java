package cn.redsoft.magician.core.gateway.filter;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * jwt 鉴权配置
 * @author fengfan 20190418
 */
//@Component
public class TokenFilter implements GlobalFilter, Ordered {

    // TODO https://blog.csdn.net/htjl575896870/article/details/88018038

    /**
     * header中token的key
     */
    private static final String LOGIN_TOKEN = "LOGIN_TOKEN";
    private final static int TEST_TOKEN_EXPIRES_MILLISECONDS = 1000 * 24 * 3600;
    /**
     * 当前登录用户ID
     */
    private static final String LOGIN_ID = "LOGIN_ID";
    /*@Resource
    private RedisService redisService;*/

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();
        //白名单
        if (UrlResolver.check(UrlWhileList.getUrlList(), url)) {
            return chain.filter(exchange);
        }
        HttpHeaders httpHeaders = request.getHeaders();
        List<String> tokens = httpHeaders.get(LOGIN_TOKEN);
        Assert.isTrue(!CollectionUtils.isEmpty(tokens), LOGIN_TOKEN + " 不能为空");
        String token = tokens.get(0);
        Claims claims = null;
        try {
            claims = JwtTokenHelper.parseJWT(token);
        } catch (RuntimeException e) {
            throw new TokenExpiredException("token过期，请重新登录");
        }
        Assert.isTrue(!(claims == null || claims.isEmpty()), LOGIN_TOKEN + " 无效");
        String id = claims.getId();
        Assert.isTrue(!StringUtils.isEmpty(id), LOGIN_TOKEN + " 无效");
        Date expiration = claims.getExpiration();
        Date today = new Date();
        long expiresMilliseconds = expiration.getTime() - claims.getNotBefore().getTime();
        //token 未过期,判断redis 缓存是否过期
        if (today.getTime() <= expiration.getTime()) {
            String cacheToken = redisService.getToken(token);
            if (StringUtils.isEmpty(cacheToken)) {
                throw new TokenExpiredException("token过期，请重新登录");
            }
            //重新刷新有效期k=y
            redisService.setToken(token, expiresMilliseconds);
            //token 已经过期,判断redis 缓存是否过期
        } else {
            String cacheToken = redisService.getToken(token);
            if (StringUtils.isEmpty(cacheToken)) {
                throw new TokenExpiredException("token过期，请重新登录");
            }
            //redis 未过期
            token = JwtTokenHelper.createJWT(id, expiresMilliseconds);
            redisService.setToken(token, expiresMilliseconds);
        }
        //响应Header 中增加可用的token
        exchange.getResponse().getHeaders().add(LOGIN_TOKEN, token);
        //请求链路中增加token 主键
        ServerHttpRequest serverHttpRequest = request.mutate().header(LOGIN_ID, id).build();
        ServerWebExchange serverWebExchange = exchange.mutate().request(serverHttpRequest).build();*/
        return chain.filter(null/*serverWebExchange*/);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
