package com.example.placement.repository;

import com.example.placement.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Additional query methods can be defined here if needed
}
