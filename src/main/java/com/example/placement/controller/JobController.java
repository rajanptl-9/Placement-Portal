package com.example.placement.controller;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.DTO.JobProfileDTO;
import com.example.placement.model.entity.DTO.JobCardDTO;
import com.example.placement.model.entity.DTO.JobDTO;
import com.example.placement.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<JobCardDTO>>> getAllJobs() {
        try {
            return jobService.getAllJobs().thenApply(ResponseEntity::ok);
        } catch (Exception e) {
            System.err.println("Controller error fetching all jobs: " + e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<JobProfileDTO>> getJobById(@PathVariable String id) {
        try {
            return jobService.getJobById(id)
                    .thenApply(opt -> opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
        } catch (Exception e) {
            System.err.println("Controller error fetching job by id: " + e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<JobDTO>> createJob(@RequestBody Job job) {
        try {
            return jobService.createJob(job).thenApply(ResponseEntity::ok);
        } catch (Exception e) {
            System.err.println("Controller error creating job: " + e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }

    @GetMapping("/eligible/{studentId}")
    public CompletableFuture<ResponseEntity<List<JobCardDTO>>> getEligibleJobs(@PathVariable String studentId) {
        try {
            return jobService.getEligibleJobsForStudent(studentId).thenApply(ResponseEntity::ok);
        } catch (Exception e) {
            System.err.println("Controller error fetching eligible jobs: " + e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Job>> updateJob(@PathVariable String id, @RequestBody Job jobDetails) {
        try {
            return jobService.updateJob(id, jobDetails)
                    .thenApply(opt -> opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
        } catch (Exception e) {
            System.err.println("Controller error updating job: " + e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteJob(@PathVariable String id) {
        try {
            return jobService.deleteJob(id).thenApply(
                    deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("Controller error deleting job: " + e.getMessage());
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }
}
