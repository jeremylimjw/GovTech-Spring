package com.govtech.app.util.pojo;

public class HttpPostUploadResponse {
    private int success;

    public HttpPostUploadResponse() {
    }

    public HttpPostUploadResponse(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

}
