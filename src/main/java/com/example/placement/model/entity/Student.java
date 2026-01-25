package com.example.placement.model.entity;

import com.example.placement.model.entity.enums.AccountStatus;
import com.example.placement.model.entity.enums.CurrentYear;
import com.example.placement.model.entity.enums.Degree;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "students")
@CompoundIndex(name = "student_unique_fields", def = "{'rollNo':1,'collegeEmail':1,'personalEmail':1,'mobile':1,'aadhaarNumber':1,'panNumber':1}", unique = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    // ===== Identity =====
    @Id
    @Setter(AccessLevel.NONE)
    private String id; // MongoDB ObjectId (auto)
    private String rollNo;

    // ===== Personal Details =====
    private String firstName;
    private String lastName;
    private LocalDate DOB;

    @Builder.Default
    private String citizenship = "Indian"; // Indian / Other

    // ===== Contact Details =====
    private String collegeEmail;
    private String personalEmail;
    private String mobile;

    // ===== Address =====
    private String city;
    private String state;
    private String pincode;

    // ===== Education =====
    private String department; // CSE, IT, ECE
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private String collegeName = "National Institute of Technology, Goa";
    private Degree highestDegree; // BTECH / MTECH
    private CurrentYear currentYear; // I, II, III, IV
    private Integer yearOfPassing;
    private Double cgpa; // 0.0 – 10.0

    // ===== Social Profiles =====
    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;

    // ===== Documents =====
    private String resumeLink;
    private String panNumber;
    private String aadhaarNumber;

    // ===== Account & Security =====
    private String password; // Store HASHED password only
    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;

    // ===== Audit =====
    @CreatedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
