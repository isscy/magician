package cn.redsoft.magician.core.auth.service;

import cn.redsoft.magician.core.auth.constant.Oauth2Property;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthCenterService {


    private final Oauth2Property oauth2Property;

    public Map<String, List<String>> noPermissionRequiredUrlList() {
        return oauth2Property.getUnCheckUrl();
    }
}
