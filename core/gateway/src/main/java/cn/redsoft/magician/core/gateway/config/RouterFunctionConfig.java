package cn.redsoft.magician.core.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpMethod;

/**
 * 路由配置
 *
 * @author fengfan 20190418
 */
@Configuration
public class RouterFunctionConfig {


    /**
     * 跨域配置
     */
    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();  // 支持的请求头
                headers.add("Access-Control-Allow-Headers", "x-requested-with, blade-auth, Content-Type, Authorization, credential, X-XSRF-TOKEN, token, username, client");
                headers.add("Access-Control-Allow-Methods", "*");
                headers.add("Access-Control-Allow-Origin", "*");
                headers.add("Access-Control-Expose-Headers", "*");
                headers.add("Access-Control-Max-Age", "36000L");
                headers.add("Access-Control-Allow-Credentials", "true");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }
}
