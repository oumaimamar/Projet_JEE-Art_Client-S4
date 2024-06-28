package com.test.Arty.services;

import com.test.Arty.entities.User;
import com.test.Arty.repositories.ArtisanRepository;
import com.test.Arty.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ArtisanRepository artisanRepository;

    public UserService(UserRepository userRepository, ArtisanRepository artisanRepository){
        this.userRepository = userRepository;
        this.artisanRepository = artisanRepository;
    }
    public User getUserByEmail(String email){
        User user = userRepository.findUserByEmail(email);
        return user;
    }
    public User createUser(User user){
        User newUser = userRepository.save(user);
        userRepository.flush();
        return newUser;
    }

}