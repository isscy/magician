package cn.redsoft.magician.core.auth.mapper;

import cn.redsoft.magician.core.common.entity.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysMenuMapper {

    List<SysMenu> getByUserId(String userId);
    List<SysMenu> getByRoleId(String roleId);
    SysMenu getById(String id);

    SysMenu getOneUrlAndRole(String url);
}
