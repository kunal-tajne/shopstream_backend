package com.shopstream.service;

import com.shopstream.exception.UserException;
import com.shopstream.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
