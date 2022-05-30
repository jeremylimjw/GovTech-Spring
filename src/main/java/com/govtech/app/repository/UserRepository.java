package com.govtech.app.repository;

import java.math.BigDecimal;
import java.util.Optional;

import com.govtech.app.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Query single User with `name` field
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> findByName(String name);
    
    // Query all users with `min`, `max`, `order`, `offset` and `limit` parameters.
    @Query("SELECT u FROM User u WHERE u.salary >= ?1 AND u.salary <= ?2")
    Page<User> findAll(BigDecimal min, BigDecimal max, Pageable pagable);

}
