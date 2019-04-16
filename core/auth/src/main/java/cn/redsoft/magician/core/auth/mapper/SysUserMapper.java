package cn.redsoft.magician.core.auth.mapper;

import cn.redsoft.magician.core.common.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {


    SysUser getById(String id);

    SysUser getByUsername(String userName);

    SysUser getByPhone(String phone);
}
