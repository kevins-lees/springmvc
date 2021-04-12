package com.vo;

public class ToServiceVo {
    private boolean success=true;
    private String msg="";
    private Object date;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ToServiceVo{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", date=" + date+
                '}';
    }
}
