package com.govtech.app.util.pojo;

import java.util.List;

import com.govtech.app.model.User;

public class HttpGetUserResponse {
    List<User> results;

    public HttpGetUserResponse(List<User> results) {
        this.results = results;
    }

    public List<User> getResults() {
        return this.results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }
}
