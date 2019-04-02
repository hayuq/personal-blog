package com.june.vo;

import javax.servlet.http.HttpServletResponse;

public enum ResponseStatus {

    /**
     * 参数错误
     */
    PARAMETER_ERROR(HttpServletResponse.SC_BAD_REQUEST, "请求参数错误"),

    PARAMETER_MISSED(HttpServletResponse.SC_BAD_REQUEST, "缺少必需的请求参数"),

    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "请求路径未找到"),

    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持{0}请求类型"),

    RECORD_CONFLICT(HttpServletResponse.SC_CONFLICT, "数据库中已存在该记录"),

    VERIFYCODE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "验证码错误"),

    VERIFYCODE_EXPIRED(HttpServletResponse.SC_BAD_REQUEST, "验证码失效"),

    OTHER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "未知错误");

    private int code;
    private String msg;

    private ResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
