package cn.redsoft.magician.core.gateway.service;

import cn.redsoft.magician.core.common.entity.R;
/**
 * 授权 service
 * @author fengfan 20190422
 */
public interface AuthService {

    /**
     * 调用签权服务，判断用户是否有权限
     * @param authentication
     * @param url
     * @param method
     */
    R authenticate(String authentication, String url, String method);

    /**
     * 判断用户请求的url是否无需鉴权直接放过
     */
    boolean ignoreAuthentication(String handleServer, String url);


    /**
     * 查看签权服务器返回结果，有权限返回true
     */
    boolean hasPermission(R result);

    boolean hasPermission(String authentication, String url, String method);





}
