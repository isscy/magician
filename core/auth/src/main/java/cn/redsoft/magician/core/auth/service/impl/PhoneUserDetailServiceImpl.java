package cn.redsoft.magician.core.auth.service.impl;

import cn.redsoft.magician.core.auth.mapper.SysUserMapper;
import cn.redsoft.magician.core.auth.service.BaseUserDetailsService;
import cn.redsoft.magician.core.common.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Deprecated
//@Service("phoneUserDetailService")
public class PhoneUserDetailServiceImpl extends BaseUserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    protected SysUser getUser(String phone) {
        SysUser sysUser = sysUserMapper.getByPhone(phone);
        return sysUser;
    }
}
