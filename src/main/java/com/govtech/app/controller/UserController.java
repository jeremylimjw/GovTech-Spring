package com.govtech.app.controller;

import java.util.List;

import com.govtech.app.model.User;
import com.govtech.app.service.IUserService;
import com.govtech.app.util.pojo.HttpErrorResponse;
import com.govtech.app.util.pojo.HttpGetUserResponse;
import com.govtech.app.util.pojo.HttpUserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    private IUserService userService;

    // GET method for retrieving Users
    @GetMapping("/api/users")
    public ResponseEntity<?> getUsers(HttpUserRequest request) {
        try {
            List<User> users = userService.findAll(request).getContent();

            HttpGetUserResponse response = new HttpGetUserResponse(users);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpErrorResponse(ex));
        }
    }
    
}
