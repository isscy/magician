package cn.redsoft.magician.core.common.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

@Data
public class R {

    private int code;
    private String message;
    private Object data;

    private final static int SUCCESS_CODE = 1;
    private final static int DEFAULT_ERROR_CODE = -1;

    public R() {}



    public R(int code, String message, Object data) {
        this(code, data);
        this.message = message;
    }

    public R(int code, Object data) {
        this();
        this.code = code;
        this.data = data;
    }

    public R success(Object data) {
        this.data = data;
        return this.success();
    }

    public R success() {
        this.code = SUCCESS_CODE;
        return this;
    }

    public R fail() {
        this.code = DEFAULT_ERROR_CODE;
        return this;
    }

    public R fail(int code, String msg) {
        if (code == SUCCESS_CODE)
            throw new RuntimeException("自定义错误编号失败！不能与SUCCESS_CODE相等");
        this.code = code;
        this.message = msg;
        return this;
    }

    public R fail(int code, String msg, Object data) {
        this.data = data;
        return this.fail(code, msg);
    }

    public R fail(String msg) {
        this.message = msg;
        return this.fail();
    }

    public String asJson(){
        return new Gson().toJson(this);
    }

    public String asJson(String format){
        Gson gson = new GsonBuilder().setDateFormat(format).create();
        return gson.toJson(this);
    }
}
