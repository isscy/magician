package cn.redsoft.magician.core.auth.mapper;

import cn.redsoft.magician.core.common.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysRoleMapper {

    List<SysRole> getByUserId(String userId);
    SysRole getById(String id);
}
