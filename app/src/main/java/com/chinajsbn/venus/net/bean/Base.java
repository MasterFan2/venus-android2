package com.chinajsbn.venus.net.bean;

/**
 * Created by MasterFan on 2015/6/25.
 * description:
 */
public class Base<T> {
    private int code;
    private boolean success;
    private int count;
    private String message;
    private T data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Base() {}

    public Base(int code, T data) {

        this.code = code;
        this.data = data;
    }
}
