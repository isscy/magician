package cn.redsoft.magician.core.auth.service;

import cn.redsoft.magician.core.auth.entity.BaseUserDetail;
import cn.redsoft.magician.core.auth.mapper.SysMenuMapper;
import cn.redsoft.magician.core.auth.mapper.SysRoleMapper;
import cn.redsoft.magician.core.auth.mapper.SysUserMapper;
import cn.redsoft.magician.core.common.constant.RedisConstant;
import cn.redsoft.magician.core.common.constant.SecurityConstant;
import cn.redsoft.magician.core.common.entity.SysMenu;
import cn.redsoft.magician.core.common.entity.SysRole;
import cn.redsoft.magician.core.common.entity.SysUser;
import cn.redsoft.magician.core.common.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service("baseUserDetailsService")
public class BaseUserDetailsService implements UserDetailsService {


    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    /*@Autowired
    private RedisTemplate defaultRedisTemplate;*/
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = null;
        if (username == null || StringUtils.isEmpty(username.trim()))
            throw new UsernameNotFoundException(username + " 不能为空");
        if (username.contains(":")) {
            String[] parameter = username.split(":");
            if (SecurityConstant.LOGIN_TYPE_PHONE.equals(parameter[0])) {
                sysUser = sysUserMapper.getByPhone(parameter[1]);
            } else { // 以后可以加上 扫码登录
                sysUser = sysUserMapper.getByUsername(parameter[1]);
            }
        } else { // 不包含就 直接认为是用户名密码登录
            sysUser = sysUserMapper.getByUsername(username);
        }
        if (sysUser == null) {
            logger.error("找不到该用户 ：" + username);
            throw new UsernameNotFoundException(username + " 该用户未注册或已删除");
        }
        List<SysRole> roles = sysRoleMapper.getByUserId(sysUser.getId());
        if (roles == null) {
            logger.warn(username + "查询角色失败！");
            roles = new ArrayList<>();
        }
        List<SysMenu> menus = sysMenuMapper.getByUserId(sysUser.getId());
        this.saveMenus(sysUser, menus);
        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(sysUser.getUserName(),
                sysUser.getPassword(), isActive(sysUser.getStatus()), true, true, true, convertToAuthorities(sysUser, roles));
        return new BaseUserDetail(sysUser, user);
    }


    private List<GrantedAuthority> convertToAuthorities(SysUser user, List<SysRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 清除 Redis 中用户的角色
        //redisUtil.hdel(RedisConstant.KEY_USER_ROLES, user.getId());
        roles.stream().filter(e -> e != null).forEach(e -> { // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
            GrantedAuthority authority = new SimpleGrantedAuthority(e.getCode());
            authorities.add(authority);
        });
        //存储角色到redis
        redisUtil.hset(RedisConstant.KEY_USER_ROLES, user.getId(), roles);
        return authorities;
    }

    private void saveMenus(SysUser user, List<SysMenu> menus) {

        redisUtil.hset(RedisConstant.KEY_USER_MENUS, user.getId(), menus);
    }

    private boolean isActive(String status) {
        return "1".equals(status);
    }

}
