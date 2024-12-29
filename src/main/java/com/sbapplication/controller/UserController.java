package com.sbapplication.controller;


import com.sbapplication.api.response.WetherResponse;
import com.sbapplication.service.WetherService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.sbapplication.entity.User;
import com.sbapplication.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WetherService wetherService;

    @GetMapping("/")
    public ResponseEntity<?> getUserById()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User authuser = userService.findByUsername(username);
        ObjectId id = authuser.getId();
        Optional<User> user =  userService.findById(id);
        
        if(user.isPresent())
        {
            //System.out.print("Inside in  Method");
            log.info("Inside in  Method");
            return new ResponseEntity<>(user.get() , HttpStatus.OK);
        }
        //System.out.print("Outside Method");
        log.info("Outside Method");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting() {
        log.info("Inside Greeting method");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WetherResponse wetherResponse = wetherService.getWether("Kolkata");
        if(wetherResponse != null) {
            return new ResponseEntity<>("Hi " + authentication.getName()
                    + " Wether feel like " + wetherResponse.getCurrent().getFeelslike() + " Observed at "
                    + wetherResponse.getCurrent().getObservationTime()
                    + " Wether is peasent like " + wetherResponse.getCurrent().getWeatherDescriptions().stream().toList()
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>("Hi" + authentication.getName(), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUserById()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUsername(username);
        //userService.deleteById(myId);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUsername(username);
        if(userInDb != null)
        {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
