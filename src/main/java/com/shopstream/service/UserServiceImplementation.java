package com.shopstream.service;

import com.shopstream.config.JwtTokenProvider;
import com.shopstream.exception.UserException;
import com.shopstream.model.User;
import com.shopstream.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Data
@NoArgsConstructor
@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public UserServiceImplementation(UserRepository userRepository,JwtTokenProvider jwtTokenProvider) {

        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;

    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user=userRepository.findById(userId);

        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email=jwtTokenProvider.getEmailFromJwtToken(jwt);

        System.out.println("email"+email);

        User user=userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("user not exist with email "+email);
        }
        System.out.println("email user"+user.getEmail());
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAllByOrderByCreatedAtDesc();
    }

}
