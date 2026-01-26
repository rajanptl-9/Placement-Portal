package com.example.placement.repository;

import com.example.placement.model.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByCollegeEmail(String collegeEmail);

    Optional<Student> findByRollNo(String rollNo);
}
