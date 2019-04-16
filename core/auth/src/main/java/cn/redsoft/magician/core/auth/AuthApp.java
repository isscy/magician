package cn.redsoft.magician.core.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.redsoft.magician.core.auth.mapper")
public class AuthApp {


    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }

    // TODO : 处理token 加上用户信息
    // TODO : 调用oauth/token的错误的话 返回给前端信息

    // TODO : 1-自定义登录方式
    // TODO : 2- 用户鉴权 - 能否访问这个请求

    // TODO : 错误处理 ： 认证的错误
    // TODO : 日志处理 traceId
}
