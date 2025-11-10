package com.r505.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r505.modele.User;
import com.r505.modele.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(String username, String password, boolean isModerator) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setIsModerator(isModerator);
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id, String username, String password, Boolean isModerator) {
        return userRepository.findById(id).map(user -> {
            if (username != null) {
                user.setUsername(username);
            }
            if (password != null) {
                user.setPassword(password);
            }
            if (isModerator != null) {
                user.setIsModerator(isModerator);
            }
            
            return userRepository.save(user);
        }).orElse(null);
    }
}
