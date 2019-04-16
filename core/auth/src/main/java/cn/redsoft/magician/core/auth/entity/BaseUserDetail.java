package cn.redsoft.magician.core.auth.entity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import cn.redsoft.magician.core.common.entity.SysUser;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 包装org.springframework.security.core.userdetails.User类 以及 包含 SysUser 用于生成 jwt 的用户信息
 * @author fengfan 20190412
 */
public class BaseUserDetail implements UserDetails, CredentialsContainer {

    private final SysUser sysUser;
    private final /*org.springframework.security.core.userdetails.*/User user;

    public BaseUserDetail(SysUser sysUser, User user) {
        this.sysUser = sysUser;
        this.user = user;
    }



    @Override
    public void eraseCredentials() {
        user.eraseCredentials();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public SysUser getSysUser() {
        return sysUser;
    }

}
