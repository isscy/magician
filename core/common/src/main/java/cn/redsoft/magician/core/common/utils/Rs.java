package cn.redsoft.magician.core.common.utils;

import cn.redsoft.magician.core.common.entity.R;

public class Rs {
    public static R success(){
        return new R().success();
    }

    public static R success(Object data){
        return new R().success(data);
    }

    public static R fail(String msg){
        return new R().fail(msg);
    }

    public static R fail(int code, String msg){
        return new R().fail(code, msg);
    }

    public static R fail(int code, String msg, Object data){
        return new R().fail(code, msg, data);
    }
}
