package cn.redsoft.magician.core.auth.controller;

import cn.redsoft.magician.core.auth.service.AuthCenterService;
import cn.redsoft.magician.core.auth.service.AuthenticationService;
import cn.redsoft.magician.core.common.entity.R;
import cn.redsoft.magician.core.common.utils.Rs;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 权限中心
 * @author fengfan 20190422
 */
@RestController
@RequestMapping("authCenter")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthCenterController {

    private final AuthCenterService authCenterService;
    private final AuthenticationService authenticationService;

    /**
     * 获取当前用户
     */
    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    /**
     * 通过token获取用户信息
     */
    @GetMapping("/getUser")
    public R getUser(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer");
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("SigningKey".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        String localUser = (String) claims.get("userinfo");
        // 拿到当前用户
        //TODO
        return null;
    }


    /**
     * 返回所有无需权限直接就能访问的URL
     */
    @GetMapping("/getNoPermissionRequiredUrl")
    public R noPermissionRequiredUrl(){
        Map<String, List<String>> map = authCenterService.noPermissionRequiredUrlList();
        return Rs.success(map);
    }

    @PostMapping("permission")
    public R decide(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {
        String userId = getUserId(request);
        Map<String, Object> decide = authenticationService.decide(request, url, method, userId);
        return Rs.success(decide);
    }


    private String getUserId(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = StringUtils.substring(header, 7);
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("Jdk_18.com".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        String userId = (String) claims.get("userId");
        // 拿到当前用户
        //TODO
        return userId;
    }




}



