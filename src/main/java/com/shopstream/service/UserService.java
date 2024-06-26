package com.shopstream.service;

import com.shopstream.exception.UserException;
import com.shopstream.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public List<User> findAllUsers();
}
