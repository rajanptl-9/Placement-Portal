package com.example.placement.service;

import com.example.placement.model.entity.Application;
import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.Student;
import com.example.placement.model.entity.DTO.ApplicationDTO;
import com.example.placement.model.entity.DTO.ApplicationCardDTO;
import com.example.placement.model.entity.DTO.ApplicationResponseDTO;
import com.example.placement.model.entity.enums.ApplicationStatus;
import com.example.placement.model.entity.enums.OfferStatus;
import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.Degree;
import com.example.placement.model.entity.enums.EligibleDegree;
import com.example.placement.repository.ApplicationRepository;
import com.example.placement.repository.JobRepository;
import com.example.placement.repository.StudentRepository;
import com.example.placement.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Async
    public CompletableFuture<ApplicationDTO> applyForJob(String studentId, String jobId) {
        try {
            if (applicationRepository.findByJobIdAndStudentId(jobId, studentId).isPresent()) {
                return CompletableFuture.failedFuture(new RuntimeException("You have already applied for this job"));
            }
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

            String errorMessage = checkEligibility(student, job);
            if (errorMessage != null) {
                return CompletableFuture.failedFuture(new RuntimeException(errorMessage));
            }
            Application application = Application.builder()
                    .studentId(studentId)
                    .jobId(jobId)
                    .applicationStatus(ApplicationStatus.APPLIED)
                    .build();

            Application saved = applicationRepository.save(application);
            return CompletableFuture.completedFuture(ApplicationDTO.from(saved));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<List<ApplicationResponseDTO>> getApplicationsByStudent(String studentId) {
        try {
            List<Application> apps = applicationRepository.findByStudentId(studentId);
            List<ApplicationResponseDTO> responses = new ArrayList<>();

            for (Application app : apps) {
                Job job = jobRepository.findById(app.getJobId()).orElse(null);
                String jobTitle = (job != null) ? job.getJobTitle() : "Unknown Job";
                String companyName = "Unknown Company";

                if (job != null && job.getCompanyId() != null) {
                    companyName = companyRepository.findById(job.getCompanyId())
                            .map(c -> c.getCompanyName())
                            .orElse("Unknown Company");
                }

                responses.add(ApplicationResponseDTO.from(app, jobTitle, companyName));
            }
            return CompletableFuture.completedFuture(responses);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<List<Application>> getApplicationsByJob(String jobId) {
        return CompletableFuture.completedFuture(applicationRepository.findByJobId(jobId));
    }

    @Async
    public CompletableFuture<List<ApplicationCardDTO>> getStudentApplicationCards(String studentId) {
        try {
            List<Application> apps = applicationRepository.findByStudentId(studentId);
            List<ApplicationCardDTO> response = apps.stream()
                    .map(ApplicationCardDTO::from)
                    .collect(Collectors.toList());
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ApplicationDTO> updateApplicationStatus(String applicationId, ApplicationStatus status,
            String remark) {
        try {
            Application app = applicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            Application updatedApp = Application.builder()
                    .id(app.getId())
                    .jobId(app.getJobId())
                    .studentId(app.getStudentId())
                    .applicationStatus(status)
                    .offerStatus(app.getOfferStatus())
                    .remark(remark != null ? remark : app.getRemark())
                    .appliedAt(app.getAppliedAt())
                    .build();

            Application saved = applicationRepository.save(updatedApp);
            return CompletableFuture.completedFuture(ApplicationDTO.from(saved));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public CompletableFuture<ApplicationDTO> updateOfferStatus(String applicationId, OfferStatus status) {
        try {
            Application app = applicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            Application updatedApp = Application.builder()
                    .id(app.getId())
                    .jobId(app.getJobId())
                    .studentId(app.getStudentId())
                    .applicationStatus(app.getApplicationStatus())
                    .offerStatus(status)
                    .remark(app.getRemark())
                    .appliedAt(app.getAppliedAt())
                    .build();

            Application saved = applicationRepository.save(updatedApp);
            return CompletableFuture.completedFuture(ApplicationDTO.from(saved));
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private String checkEligibility(Student student, Job job) {
        // 1. Branch Check
        try {
            Branch studentBranch = Branch.valueOf(student.getDepartment().toUpperCase());
            if (job.getBranchesAllowed() != null && !job.getBranchesAllowed().isEmpty()
                    && !job.getBranchesAllowed().contains(studentBranch)) {
                return "Your department is not eligible for this job";
            }
        } catch (Exception e) {

        }

        Degree studentDegree = student.getHighestDegree();
        List<EligibleDegree> eligibleDegrees = job.getEligibleDegree();

        if (studentDegree == Degree.BTECH) {
            if (eligibleDegrees != null && !eligibleDegrees.contains(EligibleDegree.BTECH)
                    && !eligibleDegrees.contains(EligibleDegree.BOTH)) {
                return "This job is not open for B.Tech students";
            }
            if (job.getGraduatingYearBtech() != null
                    && !job.getGraduatingYearBtech().equals(student.getYearOfPassing())) {
                return "Your batch is not eligible for this job";
            }
        } else if (studentDegree == Degree.MTECH) {
            if (eligibleDegrees != null && !eligibleDegrees.contains(EligibleDegree.MTECH)
                    && !eligibleDegrees.contains(EligibleDegree.BOTH)) {
                return "This job is not open for M.Tech students";
            }
            if (job.getGraduatingYearMtech() != null
                    && !job.getGraduatingYearMtech().equals(student.getYearOfPassing())) {
                return "Your batch is not eligible for this job";
            }
        }

        if (job.getMinimumCgpa() != null && (student.getCgpa() == null || student.getCgpa() < job.getMinimumCgpa())) {
            return "Your CGPA is below the minimum required CGPA (" + job.getMinimumCgpa() + ")";
        }
        return null;
    }
}
