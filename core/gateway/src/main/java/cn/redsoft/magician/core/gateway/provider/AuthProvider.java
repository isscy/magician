package cn.redsoft.magician.core.gateway.provider;


import cn.redsoft.magician.core.common.entity.R;
import cn.redsoft.magician.core.common.utils.Rs;
import org.springframework.http.HttpHeaders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign调用认证中心获取权限
 *
 * @author fengfan 20190419
 * TODO : 放到common公共可用
 */
@Component
@FeignClient(name = "auth"/*, fallback = AuthProvider.AuthProviderFallback.class*/)
public interface AuthProvider {

    /**
     * 调用签权服务，判断用户是否有权限访问该URL
     */
    @PostMapping(value = "/authCenter/permission")
    R auth(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

    /**
     * 获取无需登录就能访问的URL
     */
    @GetMapping("/authCenter/getNoPermissionRequiredUrl")
    R noPermissionRequiredUrl();

    @Component
    class AuthProviderFallback implements AuthProvider {
        /**
         * 降级统一返回无权限
         */
        @Override
        public R auth(String authentication, String url, String method) {
            return Rs.fail("调用auth-server失败！");
        }

        @Override
        public R noPermissionRequiredUrl() {
            return Rs.fail("调用auth-server失败！");
        }
    }
}
