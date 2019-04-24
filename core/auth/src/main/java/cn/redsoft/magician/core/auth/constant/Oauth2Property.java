package cn.redsoft.magician.core.auth.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Component
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Property {

    private TokenProperty token = new TokenProperty();
    /*private List<String> unCheckUrl;


    public String[] unCheckUrlArray() {
        String[] arr = new String[unCheckUrl.size()];
        return unCheckUrl.toArray(arr);
    }*/

    private Map<String, List<String>> unCheckUrl = new HashMap<>();

    /**
     * 获取无需鉴权的URL数组  此时是不需要模块key的
     */
    public String[] unCheckUrlArray() {
        List<String> urls = //unCheckUrl.entrySet().stream().map(Map.Entry::getValue).flatMap(List::stream).collect(Collectors.toList());
                unCheckUrl.values().stream().flatMap(List::stream).collect(Collectors.toList());
        String[] arr = new String[unCheckUrl.size()];
        return urls.toArray(arr);
    }


}
