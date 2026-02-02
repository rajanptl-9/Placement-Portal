package com.example.placement.model.entity.DTO;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.DriveMode;
import com.example.placement.model.entity.enums.EligibleDegree;
import com.example.placement.model.entity.enums.JobMode;
import com.example.placement.model.entity.enums.JobStatus;
import com.example.placement.model.entity.enums.OfferType;
import com.example.placement.model.entity.enums.UpcomingEvent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDTO {
    private String id;
    private String jobTitle;
    private String jobDescription;
    private String companyId;
    private String hrEmail;
    private String hrMobileNo;
    private OfferType offerType;
    private JobMode jobMode;
    private DriveMode driveMode;
    private Double stipend;
    private Double ctcPackage;
    private Double minimumCgpa;
    private List<Branch> branchesAllowed;
    private List<EligibleDegree> eligibleDegree;
    private Integer graduatingYearBtech;
    private Integer graduatingYearMtech;
    private List<String> jobLocations;
    private LocalDate driveDate;
    private Integer numberOfOpenings;
    private LocalDate applicationDeadline;
    private String selectionProcessDescription;
    private UpcomingEvent upcomingEvent;
    private List<String> externalDocLinks;
    private JobStatus jobStatus;
    private Boolean isVerified;

    public static JobDTO from(Job job) {
        if (job == null)
            return null;
        return JobDTO.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .jobDescription(job.getJobDescription())
                .companyId(job.getCompanyId())
                .hrEmail(job.getHrEmail())
                .hrMobileNo(job.getHrMobileNo())
                .offerType(job.getOfferType())
                .jobMode(job.getJobMode())
                .driveMode(job.getDriveMode())
                .stipend(job.getStipend())
                .ctcPackage(job.getCtcPackage())
                .minimumCgpa(job.getMinimumCgpa())
                .branchesAllowed(job.getBranchesAllowed())
                .eligibleDegree(job.getEligibleDegree())
                .graduatingYearBtech(job.getGraduatingYearBtech())
                .graduatingYearMtech(job.getGraduatingYearMtech())
                .jobLocations(job.getJobLocations())
                .driveDate(job.getDriveDate())
                .numberOfOpenings(job.getNumberOfOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .selectionProcessDescription(job.getSelectionProcessDescription())
                .upcomingEvent(job.getUpcomingEvent())
                .externalDocLinks(job.getExternalDocLinks())
                .jobStatus(job.getJobStatus())
                .isVerified(job.getIsVerified())
                .build();
    }
}
