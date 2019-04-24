package cn.redsoft.magician.core.common.constant;

/**
 * redis 存储的常量类
 * @author fengfan 20190403
 */
public interface RedisConstant {
    /**
     * 用户所拥有的角色
     *  map  string -> SysRole
     */
    String KEY_USER_ROLES = "userHasRoles";
    /**
     * 用户所能看的菜单
     * map string -> SysMenu
     */
    String KEY_USER_MENUS = "userHasMenus";
    /**
     * url url 所对应的角色  key - url; value - roles string
     * map string -> string
     */
    String KEY_URL_ROLES = "urlRoles";

    String KEY_UNCHECK_URL = "unCheckUrl";

}
