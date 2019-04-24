package cn.redsoft.magician.core.auth.mapper;

import cn.redsoft.magician.core.common.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysMenuMapper {

    List<SysMenu> getByUserId(String userId);
    List<SysMenu> getByRoleId(String roleId);
    SysMenu getById(String id);

    SysMenu getOneUrlAndRole(String url);

    /**
     * server用：获取一个用户所能访问的url 并且限制方法
     */
    List<SysMenu> getByUserIdAndMethod(@Param("userId") String userId,@Param("method") String method);
}
