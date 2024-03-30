package com.shopstream.repository;

import com.shopstream.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    public List<User> findAllByOrderByCreatedAtDesc();

}
