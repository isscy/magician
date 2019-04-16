package cn.redsoft.magician.core.auth.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Property {

    private TokenProperty token = new TokenProperty();
    private List<String> unCheckUrl;


    public String[] unCheckUrlArray() {
        String[] arr = new String[unCheckUrl.size()];
        return unCheckUrl.toArray(arr);
    }


}
