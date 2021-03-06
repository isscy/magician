package cn.redsoft.magician.core.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class GatewayApp {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
    //TODO 全局错误处理
    //TODO 网关超时处理

}
