package com.sbapplication.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbapplication.entity.User;
import com.sbapplication.repo.UserRepository;
import com.sbapplication.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return user;
        }
        catch(Exception e) {
            log.error("User is already created and stored in DB- Duplicate User {} :",user.getUsername());
            log.error("User is already created and stored in DB- Duplicate User");
            log.debug("User is already created and stored in DB- Duplicate User");
            log.warn("User is already created and stored in DB- Duplicate User");
            log.trace("User is already created and stored in DB- Duplicate User");
            User nuser=new User();
            return nuser;
        }
    }


    @Override
    public Optional<User> findById(ObjectId objectId) {
        return userRepository.findById(objectId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(ObjectId objectId) {
        userRepository.deleteById(objectId);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
