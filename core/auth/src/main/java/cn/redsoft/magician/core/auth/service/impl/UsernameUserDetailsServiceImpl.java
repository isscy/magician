package cn.redsoft.magician.core.auth.service.impl;

import cn.redsoft.magician.core.auth.mapper.SysUserMapper;
import cn.redsoft.magician.core.auth.service.BaseUserDetailsService;
import cn.redsoft.magician.core.common.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Deprecated
//@Service("usernameUserDetailsService")
public class UsernameUserDetailsServiceImpl extends BaseUserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    protected SysUser getUser(String userName) {

        SysUser sysUser = sysUserMapper.getByUsername(userName);
        this.logger.info("用户登录时通过用户名获取账号信息： " + userName);
        return sysUser;
    }
}
