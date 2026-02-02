package com.example.placement.controller;

import com.example.placement.model.entity.LoginCredentials;
import com.example.placement.model.entity.Student;
import com.example.placement.model.entity.DTO.StudentDTO;
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

import com.example.placement.service.JobService;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JobService jobService;

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> getStudentById(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (ResponseEntity<?>) studentRepository.findById(id)
                        .map(student -> ResponseEntity.ok(StudentDTO.from(student)))
                        .orElseGet(() -> ResponseEntity.notFound().build());
            } catch (Exception e) {
                System.err.println("Error fetching student profile: " + e.getMessage());
                return ResponseEntity.internalServerError().body("Error fetching student: " + e.getMessage());
            }
        });
    }

    @GetMapping("/{id}/jobs")
    public CompletableFuture<ResponseEntity<?>> getEligibleJobs(@PathVariable String id) {
        try {
            return jobService.getEligibleJobsForStudent(id)
                    .thenApply(jobs -> (ResponseEntity<?>) ResponseEntity.ok(jobs));
        } catch (Exception e) {
            System.err.println("Error fetching eligible jobs for student: " + e.getMessage());
            return CompletableFuture.completedFuture(
                    ResponseEntity.internalServerError().body("Error fetching jobs: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<?>> register(@RequestBody Student req) {
        try {
            return studentService.registerStudent(req).handle((dto, ex) -> {
                if (ex != null) {
                    return ResponseEntity.badRequest().body("Registration failed: " + ex.getMessage());
                }
                return ResponseEntity.ok(dto);
            });
        } catch (Exception e) {
            System.err.println("Error during student registration: " + e.getMessage());
            return CompletableFuture.completedFuture(
                    ResponseEntity.internalServerError().body("Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<?>> login(@RequestBody LoginCredentials req) {
        try {
            return studentService.loginStudent(req.getRollNo(), req.getPassword()).handle((dto, ex) -> {
                if (ex != null) {
                    return ResponseEntity.status(401).body("Login failed: " + ex.getMessage());
                }
                return ResponseEntity.ok(dto);
            });
        } catch (Exception e) {
            System.err.println("Error during student login: " + e.getMessage());
            return CompletableFuture
                    .completedFuture(ResponseEntity.internalServerError().body("Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<?>> updateProfile(@PathVariable String id, @RequestBody Student req) {
        try {
            return studentService.updateStudentProfile(id, req).handle((dto, ex) -> {
                if (ex != null) {
                    return ResponseEntity.badRequest().body("Profile update failed: " + ex.getMessage());
                }
                return ResponseEntity.ok(dto);
            });
        } catch (Exception e) {
            System.err.println("Error during student profile update: " + e.getMessage());
            return CompletableFuture.completedFuture(
                    ResponseEntity.internalServerError().body("Profile update failed: " + e.getMessage()));
        }
    }
}
