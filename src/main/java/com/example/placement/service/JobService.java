package com.example.placement.service;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.DTO.JobProfileDTO;
import com.example.placement.model.entity.DTO.JobCardDTO;
import com.example.placement.model.entity.DTO.JobDTO;
import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.Degree;
import com.example.placement.model.entity.enums.EligibleDegree;
import com.example.placement.model.entity.DTO.CompanyDTO;
import com.example.placement.repository.JobRepository;
import com.example.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyService companyService;

    @Async
    public CompletableFuture<List<JobCardDTO>> getAllJobs() {
        try {
            List<Job> jobs = jobRepository.findAll();
            List<CompletableFuture<JobCardDTO>> futures = jobs.stream()
                    .map(this::mapToCardDTOAsync)
                    .collect(Collectors.toList());

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList()));
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
            return jobRepository.findById(id)
                    .map(job -> mapToProfileDTOAsync(job).thenApply(Optional::of))
                    .orElse(CompletableFuture.completedFuture(Optional.empty()));
        } catch (Exception e) {
            System.err.println("Error fetching job by id " + id + ": " + e.getMessage());
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    @Async
    public CompletableFuture<JobDTO> createJob(JobDTO jobDTO) {
        try {
            Job job = Job.builder()
                    .jobTitle(jobDTO.getJobTitle())
                    .jobDescription(jobDTO.getJobDescription())
                    .companyId(jobDTO.getCompanyId())
                    .hrEmail(jobDTO.getHrEmail())
                    .hrMobileNo(jobDTO.getHrMobileNo())
                    .offerType(jobDTO.getOfferType())
                    .jobMode(jobDTO.getJobMode())
                    .driveMode(jobDTO.getDriveMode())
                    .stipend(jobDTO.getStipend())
                    .ctcPackage(jobDTO.getCtcPackage())
                    .minimumCgpa(jobDTO.getMinimumCgpa())
                    .branchesAllowed(jobDTO.getBranchesAllowed())
                    .eligibleDegree(jobDTO.getEligibleDegree())
                    .graduatingYearBtech(jobDTO.getGraduatingYearBtech())
                    .graduatingYearMtech(jobDTO.getGraduatingYearMtech())
                    .jobLocations(jobDTO.getJobLocations())
                    .driveDate(jobDTO.getDriveDate())
                    .numberOfOpenings(jobDTO.getNumberOfOpenings())
                    .applicationDeadline(jobDTO.getApplicationDeadline())
                    .selectionProcessDescription(jobDTO.getSelectionProcessDescription())
                    .upcomingEvent(jobDTO.getUpcomingEvent())
                    .externalDocLinks(jobDTO.getExternalDocLinks())
                    .build();

            Job savedJob = jobRepository.save(job);
            return CompletableFuture.completedFuture(JobDTO.from(savedJob));
        } catch (Exception e) {
            System.err.println("Error creating job: " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Async
    public CompletableFuture<Optional<Job>> updateJob(String id, JobDTO jobDetails) {
        try {
            if (id == null)
                return CompletableFuture.completedFuture(Optional.empty());
            Optional<Job> updated = jobRepository.findById(id).map(existingJob -> {
                if (jobDetails.getJobTitle() != null)
                    existingJob.setJobTitle(jobDetails.getJobTitle());
                if (jobDetails.getJobDescription() != null)
                    existingJob.setJobDescription(jobDetails.getJobDescription());
                if (jobDetails.getHrEmail() != null)
                    existingJob.setHrEmail(jobDetails.getHrEmail());
                if (jobDetails.getHrMobileNo() != null)
                    existingJob.setHrMobileNo(jobDetails.getHrMobileNo());
                if (jobDetails.getOfferType() != null)
                    existingJob.setOfferType(jobDetails.getOfferType());
                if (jobDetails.getJobMode() != null)
                    existingJob.setJobMode(jobDetails.getJobMode());
                if (jobDetails.getDriveMode() != null)
                    existingJob.setDriveMode(jobDetails.getDriveMode());
                if (jobDetails.getStipend() != null)
                    existingJob.setStipend(jobDetails.getStipend());
                if (jobDetails.getCtcPackage() != null)
                    existingJob.setCtcPackage(jobDetails.getCtcPackage());
                if (jobDetails.getMinimumCgpa() != null)
                    existingJob.setMinimumCgpa(jobDetails.getMinimumCgpa());
                if (jobDetails.getBranchesAllowed() != null)
                    existingJob.setBranchesAllowed(jobDetails.getBranchesAllowed());
                if (jobDetails.getEligibleDegree() != null)
                    existingJob.setEligibleDegree(jobDetails.getEligibleDegree());
                if (jobDetails.getGraduatingYearBtech() != null)
                    existingJob.setGraduatingYearBtech(jobDetails.getGraduatingYearBtech());
                if (jobDetails.getGraduatingYearMtech() != null)
                    existingJob.setGraduatingYearMtech(jobDetails.getGraduatingYearMtech());
                if (jobDetails.getJobLocations() != null)
                    existingJob.setJobLocations(jobDetails.getJobLocations());
                if (jobDetails.getDriveDate() != null)
                    existingJob.setDriveDate(jobDetails.getDriveDate());
                if (jobDetails.getNumberOfOpenings() != null)
                    existingJob.setNumberOfOpenings(jobDetails.getNumberOfOpenings());
                if (jobDetails.getApplicationDeadline() != null)
                    existingJob.setApplicationDeadline(jobDetails.getApplicationDeadline());
                if (jobDetails.getSelectionProcessDescription() != null)
                    existingJob.setSelectionProcessDescription(jobDetails.getSelectionProcessDescription());
                if (jobDetails.getUpcomingEvent() != null)
                    existingJob.setUpcomingEvent(jobDetails.getUpcomingEvent());
                if (jobDetails.getExternalDocLinks() != null)
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

            Optional<List<Job>> jobsOpt = studentRepository.findById(studentId).map(student -> {
                Branch studentBranch;
                try {
                    studentBranch = Branch.valueOf(student.getDepartment().toUpperCase());
                } catch (IllegalArgumentException | NullPointerException e) {
                    return null;
                }

                Degree degree = student.getHighestDegree();
                Integer yop = student.getYearOfPassing();
                List<Job> eligibleJobs;

                if (degree == Degree.BTECH) {
                    eligibleJobs = jobRepository.findEligibleJobsForBtech(studentBranch, EligibleDegree.BTECH, yop);
                } else if (degree == Degree.MTECH) {
                    eligibleJobs = jobRepository.findEligibleJobsForMtech(studentBranch, EligibleDegree.MTECH, yop);
                } else {
                    return null;
                }

                return eligibleJobs.stream()
                        .filter(job -> student.getCgpa() == null || job.getMinimumCgpa() == null
                                || student.getCgpa() >= job.getMinimumCgpa())
                        .collect(Collectors.toList());
            });

            if (jobsOpt.isEmpty() || jobsOpt.get() == null) {
                return CompletableFuture.completedFuture(new ArrayList<>());
            }

            List<CompletableFuture<JobCardDTO>> futures = jobsOpt.get().stream()
                    .map(this::mapToCardDTOAsync)
                    .collect(Collectors.toList());

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList()));
        } catch (Exception e) {
            System.err.println("Error calculating eligibility for student " + studentId + ": " + e.getMessage());
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
    }

    private CompletableFuture<JobCardDTO> mapToCardDTOAsync(Job job) {
        if (job.getCompanyId() == null || job.getCompanyId().isEmpty()) {
            return CompletableFuture.completedFuture(JobCardDTO.from(job, (CompanyDTO) null));
        }
        return companyService.getCompanyById(job.getCompanyId())
                .thenApply(opt -> JobCardDTO.from(job, opt.orElse(null)));
    }

    private CompletableFuture<JobProfileDTO> mapToProfileDTOAsync(Job job) {
        if (job.getCompanyId() == null || job.getCompanyId().isEmpty()) {
            return CompletableFuture.completedFuture(JobProfileDTO.from(job, (CompanyDTO) null));
        }
        return companyService.getCompanyById(job.getCompanyId())
                .thenApply(opt -> JobProfileDTO.from(job, opt.orElse(null)));
    }
}
