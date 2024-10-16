package com.pasc.business.workspace.bean;

public class BizModel<T> {

    public static enum Source {
        NET, CACHE
    }

    private boolean success; // 是否成功
    private Source source; // 来源
    private String errorMsg; // 错误信息
    private T data; // 数据

    public boolean fromNet() {
        if (Source.NET.equals(source)) {
            return true;
        }
        return false;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
