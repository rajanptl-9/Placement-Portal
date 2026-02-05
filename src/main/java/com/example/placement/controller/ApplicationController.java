package com.example.placement.controller;

import com.example.placement.model.entity.DTO.ApplicationCardDTO;
import com.example.placement.model.entity.DTO.ApplicationResponseDTO;
import com.example.placement.model.entity.enums.ApplicationStatus;
import com.example.placement.model.entity.enums.OfferStatus;
import com.example.placement.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public CompletableFuture<ResponseEntity<?>> apply(@RequestBody Map<String, String> request) {
        String studentId = request.get("studentId");
        String jobId = request.get("jobId");

        if (studentId == null || jobId == null) {
            return CompletableFuture
                    .completedFuture(ResponseEntity.badRequest().body("studentId and jobId are required"));
        }

        return applicationService.applyForJob(studentId, jobId)
                .handle((dto, ex) -> {
                    if (ex != null) {
                        return ResponseEntity.badRequest().body(ex.getMessage());
                    }
                    return ResponseEntity.ok(dto);
                });
    }

    @GetMapping("/student/{studentId}")
    public CompletableFuture<ResponseEntity<List<ApplicationResponseDTO>>> getStudentApplications(
            @PathVariable String studentId) {
        return applicationService.getApplicationsByStudent(studentId)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/student/{studentId}/cards")
    public CompletableFuture<ResponseEntity<List<ApplicationCardDTO>>> getStudentApplicationCards(
            @PathVariable String studentId) {
        return applicationService.getStudentApplicationCards(studentId)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/job/{jobId}")
    public CompletableFuture<ResponseEntity<?>> getJobApplications(@PathVariable String jobId) {
        return applicationService.getApplicationsByJob(jobId)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}/status")
    public CompletableFuture<ResponseEntity<?>> updateStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {

        try {
            ApplicationStatus status = ApplicationStatus.valueOf(request.get("status"));
            String remark = request.get("remark");
            return applicationService.updateApplicationStatus(id, status, remark)
                    .handle((dto, ex) -> {
                        if (ex != null) {
                            return ResponseEntity.badRequest().body(ex.getMessage());
                        }
                        return ResponseEntity.ok(dto);
                    });
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid status value"));
        }
    }

    @PutMapping("/{id}/offer-status")
    public CompletableFuture<ResponseEntity<?>> updateOfferStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {

        try {
            OfferStatus status = OfferStatus.valueOf(request.get("status"));
            return applicationService.updateOfferStatus(id, status)
                    .handle((dto, ex) -> {
                        if (ex != null) {
                            return ResponseEntity.badRequest().body(ex.getMessage());
                        }
                        return ResponseEntity.ok(dto);
                    });
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid offer status value"));
        }
    }
}
