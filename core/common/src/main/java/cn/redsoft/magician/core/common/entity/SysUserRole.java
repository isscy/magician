package cn.redsoft.magician.core.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserRole {

    private String id;
    private String userId;
    private String roleId;
    private Date relTime;
}
