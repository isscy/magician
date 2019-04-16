package cn.redsoft.magician.core.common.entity;

import lombok.Data;

import java.util.Date;
@Data
public class SysMenu {

    private String id;
    private String type;
    private String name;
    private String title;
    private String parentId;
    private int level;
    private String url;
    private int orderNum;
    private String perms;
    private String icon;
    private String createBy;
    private Date createTime;
    private String remark;

    private String ofRoles;
}
