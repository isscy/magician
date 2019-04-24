package cn.redsoft.magician.core.gateway.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单放过配置
 *
 * @author fengfan 20190418
 */

//@Configuration
public class WhileUrlConfig implements InitializingBean {


    private final static List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //后台-获取图形验证码
        /*URL_LIST.add("/xxx1-service/v1/validateCode");
        //APP登录注册
        URL_LIST.add("/xxx2-service/v1/token/app/login/*");
        URL_LIST.add("/xxx3-service/v1/token/app/register/*");
        //网页登录注册
        URL_LIST.add("/xxx4-service/v1/token/mweb/login/*");
        URL_LIST.add("/xxx5-service/v1/token/mweb/register/*");
        //获取短信验证码
        URL_LIST.add("/xxx6-service/v1/message/login");*/

    }

    public static List<String> getUrlList() {
        return urls;
    }
}
