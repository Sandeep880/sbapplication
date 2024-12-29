package com.sbapplication.service;


import org.bson.types.ObjectId;

import com.sbapplication.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User saveUser(User user);

    public User saveNewUser(User user);

    public Optional<User> findById(ObjectId objectId);

    public List<User> getAll() ;

    public void deleteById(ObjectId objectId);

    public User findByUsername(String username);


    void deleteByUsername(String username);
}
