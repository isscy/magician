package cn.redsoft.magician.core.gateway.handler;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RequestMapping
@Component
public class HystrixErrorHandler {


    @RequestMapping("/hystrixTimeout")
    public String hystrixTimeout() {
        return "gateway触发了断路由";
    }

    @HystrixCommand(commandKey = "adminHystrixCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")}
    )
    public Map adminHystrixCommand() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "gateway触发了adminHystrixCommand");
        return map;
    }
}
