package com.example.placement.controller;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.LoginCredentials;
import com.example.placement.model.entity.Student;
import com.example.placement.model.entity.DTO.JobProfileDTO;
import com.example.placement.model.entity.DTO.StudentDTO;
import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.Degree;
import com.example.placement.model.entity.enums.EligibleDegree;
import com.example.placement.repository.JobRepository;
import com.example.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.placement.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        try {
            return studentRepository.findById(id)
                    .map(student -> ResponseEntity.ok(StudentDTO.from(student)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching student: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/jobs")
    public ResponseEntity<?> getEligibleJobs(@PathVariable String id) {
        try {
            return studentRepository.findById(id).map(student -> {
                Branch studentBranch;
                try {
                    studentBranch = Branch.valueOf(student.getDepartment().toUpperCase());
                } catch (IllegalArgumentException | NullPointerException e) {
                    // Return empty list if branch is invalid or not mapped
                    return ResponseEntity.ok(Collections.<JobProfileDTO>emptyList());
                }

                Degree degree = student.getHighestDegree();
                Integer yop = student.getYearOfPassing();
                List<Job> eligibleJobs;

                if (degree == Degree.BTECH) {
                    eligibleJobs = jobRepository.findEligibleJobsForBtech(studentBranch, EligibleDegree.BTECH, yop);
                } else if (degree == Degree.MTECH) {
                    eligibleJobs = jobRepository.findEligibleJobsForMtech(studentBranch, EligibleDegree.MTECH, yop);
                } else {
                    return ResponseEntity.ok(Collections.<JobProfileDTO>emptyList());
                }

                List<JobProfileDTO> jobDTOs = eligibleJobs.stream()
                        .filter(job -> student.getCgpa() == null || job.getMinimumCgpa() == null
                                || student.getCgpa() >= job.getMinimumCgpa())
                        .map(JobProfileDTO::from)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(jobDTOs);
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching jobs: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student req) {
        try {
            if (req.getDOB() != null && !req.getDOB().toString().isEmpty()) {
                req.setDOB(java.time.LocalDate.parse(req.getDOB().toString()));
            }

            return ResponseEntity.ok(studentService.registerStudent(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials req) {
        try {
            return ResponseEntity.ok(studentService.loginStudent(req.getRollNo(), req.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable String id, @RequestBody Student req) {
        try {
            return ResponseEntity.ok(studentService.updateStudentProfile(id, req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Profile update failed: " + e.getMessage());
        }
    }
}
