package cn.itnanls.util;

import java.util.Map;

/**
 *统一的返回
 */
public class R {

    /*编码*/
    private int code;
    /*附加信息*/
    private String msg;

    private Map<String,String>otherMsg;

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Map<String, String> getOtherMsg() {
        return otherMsg;
    }

    public void setOtherMsg(Map<String, String> otherMsg) {
        this.otherMsg = otherMsg;
    }

    public static  R of(ResultCode resultCode){
        return new R(resultCode.getCode(),resultCode.getMsg());
    }

    public static  R of(int code ,String msg){
        return new R(code,msg);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
