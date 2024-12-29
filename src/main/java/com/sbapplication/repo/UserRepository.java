package com.sbapplication.repo;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sbapplication.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    public User findByUsername(String username);

    void deleteByUsername(String username);
}
