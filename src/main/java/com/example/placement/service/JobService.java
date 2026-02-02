package com.example.placement.service;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.Company;
import com.example.placement.model.entity.DTO.JobProfileDTO;
import com.example.placement.model.entity.DTO.JobCardDTO;
import com.example.placement.model.entity.DTO.JobDTO;
import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.Degree;
import com.example.placement.model.entity.enums.EligibleDegree;
import com.example.placement.repository.JobRepository;
import com.example.placement.repository.CompanyRepository;
import com.example.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Async
    public CompletableFuture<List<JobCardDTO>> getAllJobs() {
        try {
            List<JobCardDTO> jobs = jobRepository.findAll().stream()
                    .map(this::mapToCardDTO)
                    .collect(Collectors.toList());
            return CompletableFuture.completedFuture(jobs);
        } catch (Exception e) {
            System.err.println("Error fetching all jobs: " + e.getMessage());
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
    }

    @Async
    public CompletableFuture<Optional<JobProfileDTO>> getJobById(String id) {
        try {
            if (id == null)
                return CompletableFuture.completedFuture(Optional.empty());
            Optional<JobProfileDTO> response = jobRepository.findById(id).map(this::mapToProfileDTO);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            System.err.println("Error fetching job by id " + id + ": " + e.getMessage());
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    @Async
    public CompletableFuture<JobDTO> createJob(Job job) {
        try {
            Job savedJob = jobRepository.save(job);
            return CompletableFuture.completedFuture(JobDTO.from(savedJob));
        } catch (Exception e) {
            System.err.println("Error creating job: " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Async
    public CompletableFuture<Optional<Job>> updateJob(String id, Job jobDetails) {
        try {
            if (id == null)
                return CompletableFuture.completedFuture(Optional.empty());
            Optional<Job> updated = jobRepository.findById(id).map(existingJob -> {
                existingJob.setJobTitle(jobDetails.getJobTitle());
                existingJob.setJobDescription(jobDetails.getJobDescription());
                existingJob.setHrEmail(jobDetails.getHrEmail());
                existingJob.setHrMobileNo(jobDetails.getHrMobileNo());
                existingJob.setOfferType(jobDetails.getOfferType());
                existingJob.setJobMode(jobDetails.getJobMode());
                existingJob.setDriveMode(jobDetails.getDriveMode());
                existingJob.setStipend(jobDetails.getStipend());
                existingJob.setCtcPackage(jobDetails.getCtcPackage());
                existingJob.setMinimumCgpa(jobDetails.getMinimumCgpa());
                existingJob.setBranchesAllowed(jobDetails.getBranchesAllowed());
                existingJob.setEligibleDegree(jobDetails.getEligibleDegree());
                existingJob.setGraduatingYearBtech(jobDetails.getGraduatingYearBtech());
                existingJob.setGraduatingYearMtech(jobDetails.getGraduatingYearMtech());
                existingJob.setJobLocations(jobDetails.getJobLocations());
                existingJob.setDriveDate(jobDetails.getDriveDate());
                existingJob.setNumberOfOpenings(jobDetails.getNumberOfOpenings());
                existingJob.setApplicationDeadline(jobDetails.getApplicationDeadline());
                existingJob.setSelectionProcessDescription(jobDetails.getSelectionProcessDescription());
                existingJob.setUpcomingEvent(jobDetails.getUpcomingEvent());
                existingJob.setExternalDocLinks(jobDetails.getExternalDocLinks());
                return jobRepository.save(existingJob);
            });
            return CompletableFuture.completedFuture(updated);
        } catch (Exception e) {
            System.err.println("Error updating job " + id + ": " + e.getMessage());
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    @Async
    public CompletableFuture<Boolean> deleteJob(String id) {
        try {
            if (id != null && jobRepository.existsById(id)) {
                jobRepository.deleteById(id);
                return CompletableFuture.completedFuture(true);
            }
            return CompletableFuture.completedFuture(false);
        } catch (Exception e) {
            System.err.println("Error deleting job " + id + ": " + e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }

    @Async
    public CompletableFuture<List<JobCardDTO>> getEligibleJobsForStudent(String studentId) {
        try {
            if (studentId == null)
                return CompletableFuture.completedFuture(new ArrayList<>());

            List<JobCardDTO> eligibleJobs = studentRepository.findById(studentId).map(student -> {
                Branch studentBranch;
                try {
                    studentBranch = Branch.valueOf(student.getDepartment().toUpperCase());
                } catch (IllegalArgumentException | NullPointerException e) {
                    return new ArrayList<JobCardDTO>();
                }

                Degree degree = student.getHighestDegree();
                Integer yop = student.getYearOfPassing();
                List<Job> jobs;

                if (degree == Degree.BTECH) {
                    jobs = jobRepository.findEligibleJobsForBtech(studentBranch, EligibleDegree.BTECH, yop);
                } else if (degree == Degree.MTECH) {
                    jobs = jobRepository.findEligibleJobsForMtech(studentBranch, EligibleDegree.MTECH, yop);
                } else {
                    return new ArrayList<JobCardDTO>();
                }

                return jobs.stream()
                        .filter(job -> student.getCgpa() == null || job.getMinimumCgpa() == null
                                || student.getCgpa() >= job.getMinimumCgpa())
                        .map(this::mapToCardDTO)
                        .collect(Collectors.toList());
            }).orElse(new ArrayList<JobCardDTO>());

            return CompletableFuture.completedFuture(eligibleJobs);
        } catch (Exception e) {
            System.err.println("Error calculating eligibility for student " + studentId + ": " + e.getMessage());
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
    }

    private JobCardDTO mapToCardDTO(Job job) {
        try {
            Company company = null;
            if (job.getCompanyId() != null && !job.getCompanyId().isEmpty()) {
                company = companyRepository.findById(job.getCompanyId()).orElse(null);
            }
            return JobCardDTO.from(job, company);
        } catch (Exception e) {
            System.err.println("Error mapping job to CardDTO: " + e.getMessage());
            return JobCardDTO.from(job, null);
        }
    }

    private JobProfileDTO mapToProfileDTO(Job job) {
        try {
            Company company = null;
            if (job.getCompanyId() != null && !job.getCompanyId().isEmpty()) {
                company = companyRepository.findById(job.getCompanyId()).orElse(null);
            }
            return JobProfileDTO.from(job, company);
        } catch (Exception e) {
            System.err.println("Error mapping job to ProfileDTO: " + e.getMessage());
            return JobProfileDTO.from(job, null);
        }
    }
}
