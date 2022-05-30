package com.govtech.app.service;

import java.util.Optional;

import com.govtech.app.model.User;
import com.govtech.app.repository.UserRepository;
import com.govtech.app.util.common.OffsetBasedPageRequest;
import com.govtech.app.util.exception.UserEntityException;
import com.govtech.app.util.exception.UserEntityNotFoundException;
import com.govtech.app.util.pojo.HttpUserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    // Retrieve all Users
    public Page<User> findAll(HttpUserRequest request) {
        return (Page<User>) repository.findAll(
            request.getMin(), 
            request.getMax(), 
            new OffsetBasedPageRequest(
                request.getLimit(), 
                request.getOffset(), 
                request.getSorter()
            )
        );
    }

    @Override
    // Find a single user by 'name'
    public Optional<User> findByName(String name) throws UserEntityException {
        if (name == null) {
            throw new UserEntityException("User 'name' is required.");
        }
        return repository.findByName(name);
    }

    @Override
    // Create a new user
    public void save(User user) throws UserEntityException {
        // Validation
        validateUserEntity(user);

        // Unique name validation
        Optional<User> optionalUser = repository.findByName(user.getName());
        if (optionalUser.isPresent()) {
            throw new UserEntityException("Username" + user.getName() + " already exists.");
        }

        // Save the user
        repository.save(user);
    }

    @Override
    // Update an existing user
    public void update(User newUser) throws UserEntityException, UserEntityNotFoundException {
        // Validation
        validateUserEntity(newUser);

        // Check if user exists
        Optional<User> results = repository.findByName(newUser.getName());
        if (results.isEmpty()) {
            throw new UserEntityNotFoundException("Username" + newUser.getName() + " does not exist.");
        }

        // Update the user attributes
        User foundUser = results.get();
        foundUser.setSalary(newUser.getSalary());

        repository.save(foundUser);
    }

    // Simple validation for User entity
    private User validateUserEntity(User user) throws UserEntityException {
        if (user.getName() == null) throw new UserEntityException("User 'name' field is required.");
        if (user.getName().trim().equals("")) throw new UserEntityException("User 'name' field must be at least 1 character.");
        if (user.getSalary() == null) throw new UserEntityException("User 'salary' field is required.");

        return user;
    }
    
}
