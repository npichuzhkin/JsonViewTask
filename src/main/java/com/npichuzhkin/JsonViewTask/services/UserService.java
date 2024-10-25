package com.npichuzhkin.JsonViewTask.services;

import com.npichuzhkin.JsonViewTask.models.User;
import com.npichuzhkin.JsonViewTask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user){
        userRepository.save(user);
    }

    public User readOne(UUID id){
        return userRepository.findById(id).stream().findAny().orElseThrow();
    }

    public List<User> readAll(){
        return userRepository.findAll();
    }

    public void update(UUID id, User updatedUser){
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    public void delete(UUID id){
        userRepository.deleteById(id);
    }
}
