package com.example.placement.model.entity;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.DriveMode;
import com.example.placement.model.entity.enums.EligibleDegree;
import com.example.placement.model.entity.enums.JobMode;
import com.example.placement.model.entity.enums.JobStatus;
import com.example.placement.model.entity.enums.OfferType;
import com.example.placement.model.entity.enums.UpcomingEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CompoundIndex(name = "job_unique_fields", def = "{'companyId':1,'hrEmail':1, 'hrMobileNo':1}", unique = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jobs")
public class Job {

    @Id
    private String id;

    /* ===== BASIC JOB DETAILS ===== */
    private String jobTitle;
    private String jobDescription; // brief description

    /* ===== COMPANY ===== */
    @Setter(AccessLevel.NONE)
    private String companyId; // from Company model
    private String hrEmail; // HR official email
    private String hrMobileNo; // HR official mobile number

    /* ===== JOB TYPE & MODE ===== */
    private OfferType offerType; // INTERN / FULL_TIME / BOTH
    @Builder.Default
    private JobMode jobMode = JobMode.ON_SITE; // ON_SITE / REMOTE / HYBRID
    @Builder.Default
    private DriveMode driveMode = DriveMode.ONLINE; // ONLINE / OFFLINE

    /* ===== COMPENSATION ===== */
    private Double stipend; // if internship
    private Double ctcPackage; // if full-time or internship + full-time

    /* ===== ELIGIBILITY ===== */
    private Double minimumCgpa;
    private List<Branch> branchesAllowed;
    private List<EligibleDegree> eligibleDegree; // BTECH / MTECH / BOTH
    private Integer graduatingYearBtech;
    private Integer graduatingYearMtech;

    /* ===== LOCATION ===== */
    private List<String> jobLocations;

    /* ===== DRIVE DETAILS ===== */
    private LocalDate driveDate;
    private Integer numberOfOpenings;
    private LocalDate applicationDeadline;
    private String selectionProcessDescription;

    @Builder.Default
    private UpcomingEvent upcomingEvent = UpcomingEvent.SHORTLISTING;

    /* ===== DOCUMENTS ===== */
    private List<String> externalDocLinks;

    /* ===== STATUS & AUDIT ===== */
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private JobStatus jobStatus = JobStatus.OPEN; // OPEN / CLOSED / ON_HOLD

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Boolean isVerified = false;

    @CreatedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
