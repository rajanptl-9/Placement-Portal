package com.example.placement.repository;

import com.example.placement.model.entity.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByStudentId(String studentId);

    List<Application> findByJobId(String jobId);

    Optional<Application> findByJobIdAndStudentId(String jobId, String studentId);
}
