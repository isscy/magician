package cn.redsoft.magician.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务中心
 * @author fengfan 20190327
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApp {


    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class, args);
    }
}
