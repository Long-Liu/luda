package edu.zygxy.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;


public class BaseException extends Exception {

    private Integer code;
    private String msg;
    private Date time;
    private String errorStack;

    public BaseException(Integer code, String message, Exception e) {
        this.code = code;
        this.msg = message;
        if (e == null) {
            errorStack = null;
        } else {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            this.errorStack = sw.toString();
        }
        this.time = new Date();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getErrorStack() {
        return errorStack;
    }

    public void setErrorStack(String errorStack) {
        this.errorStack = errorStack;
    }
}
