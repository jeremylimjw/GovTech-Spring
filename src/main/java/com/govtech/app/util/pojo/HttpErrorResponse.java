package com.govtech.app.util.pojo;

public class HttpErrorResponse {
    private String message;

    public HttpErrorResponse(Exception ex) {
        this.message = ex.getClass().getSimpleName() + ": " + ex.getMessage();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
