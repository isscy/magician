package cn.redsoft.magician.core.common.constant;

import java.util.Arrays;

/**
 * 错误码
 * @author fengfan 20190419
 */
public enum ErrorCodeEnum {

    SERVER_ERROR(500, "系统错误"),
    SERVER_ERROR_501(501, "微服务调用错误"),

    CLIENT_ERROR(400, "用户错误"),
    CLIENT_ERROR_401(401, "认证错误"),
    CLIENT_ERROR_403(403, "无权访问");

    private int code;
    private String message;

    ErrorCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public String getMessageByCode(int code){
        for (ErrorCodeEnum entity: ErrorCodeEnum.values()){
            if (code == entity.getCode())
                return message;
        }
        return "";
    }

    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }

}
