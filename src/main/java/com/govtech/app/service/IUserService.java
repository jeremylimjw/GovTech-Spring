package com.govtech.app.service;

import java.util.Optional;

import com.govtech.app.model.User;
import com.govtech.app.util.exception.UserEntityException;
import com.govtech.app.util.exception.UserEntityNotFoundException;
import com.govtech.app.util.pojo.HttpUserRequest;

import org.springframework.data.domain.Page;


public interface IUserService {
    Page<User> findAll(HttpUserRequest request);
    public Optional<User> findByName(String name) throws UserEntityException;
    void save(User user) throws UserEntityException;
    public void update(User user) throws UserEntityException, UserEntityNotFoundException;
}
