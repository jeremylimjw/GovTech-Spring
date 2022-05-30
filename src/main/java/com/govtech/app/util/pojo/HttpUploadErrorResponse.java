package com.govtech.app.util.pojo;

public class HttpUploadErrorResponse extends HttpErrorResponse {
    private int success = 0;

    public HttpUploadErrorResponse(Exception ex) {
        super(ex);
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
