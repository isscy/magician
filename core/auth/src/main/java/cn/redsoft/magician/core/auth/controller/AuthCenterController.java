package cn.redsoft.magician.core.auth.controller;

import cn.redsoft.magician.core.common.entity.R;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("authCenter")
public class AuthCenterController {


    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/getUser")
    public R getUser(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer");
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("SigningKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        String localUser = (String) claims.get("userinfo");
        // 拿到当前用户
        //TODO
        return null;
    }


}



