package cn.redsoft.magician.core.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户表
 */
@Data
public class SysUser implements Serializable {

    private String id;
    private String userName;
    private String password;
    private String nickName;
    private String phone;
    private Date createTime;
    private String type;
    private String status;
    private String delFlag;


    public static SysUser fromPrincipal(Object principal){
        if (principal == null)
            return new SysUser();

        return null;
        //FIXME
    }
}
