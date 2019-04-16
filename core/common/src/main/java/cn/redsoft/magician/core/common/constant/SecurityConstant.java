package cn.redsoft.magician.core.common.constant;

/**
 * 权限相关的常量
 *
 * @author fengfan 20190327
 */
public interface SecurityConstant {


    /**
     * 登录方式
     */
    String LOGIN_TYPE_PHONE = "PHONE";
    String LOGIN_TYPE_USERNAME = "USERNAME";

    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";
    /**
     * 前缀
     */
    String PROJECT_PREFIX = "qdzw";

    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";
    /**
     * 项目的license
     */
    String PROJECT_LICENSE = "made by redSoft of china";

    /**
     * 封装用户信息的key
     */
    String USER_INFO = "userInfo";

    /**
     * 内部
     */
    String FROM_IN = "Y";

    /**
     * 标志
     */
    String FROM = "from";

    /**
     * 手机号登录URL
     */
    String MOBILE_TOKEN_URL = "/mobile/token";

    /**
     * 默认登录URL
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * grant_type
     */
    String REFRESH_TOKEN = "refresh_token";



}
