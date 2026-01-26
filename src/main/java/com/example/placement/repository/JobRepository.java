package com.example.placement.repository;

import com.example.placement.model.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.EligibleDegree;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {

    @Query("{ 'jobStatus': 'OPEN', 'isVerified': true, 'branchesAllowed': ?0, " +
            "'eligibleDegree': { $in: [?1, 'BOTH'] }, 'graduatingYearBtech': ?2 }")
    List<Job> findEligibleJobsForBtech(Branch branch, EligibleDegree degree, Integer year);

    @Query("{ 'jobStatus': 'OPEN', 'isVerified': true, 'branchesAllowed': ?0, " +
            "'eligibleDegree': { $in: [?1, 'BOTH'] }, 'graduatingYearMtech': ?2 }")
    List<Job> findEligibleJobsForMtech(Branch branch, EligibleDegree degree, Integer year);
}
