package cn.redsoft.magician.core.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigApp {


    public static void main(String[] args) {
        SpringApplication.run(ConfigApp.class, args);
    }

    // TODO 安装 rabbitmq 消息总线刷新配置 https://blog.csdn.net/weixin_43430036/article/details/83993919
    // TODO 怎么在docker中使用rabbitmq 是公用一个还是 一个container用一个呢？
}
