package com.example.placement.model.entity.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.Company;
import com.example.placement.model.entity.enums.JobMode;
import com.example.placement.model.entity.enums.JobStatus;
import com.example.placement.model.entity.enums.OfferType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobProfileDTO {
    private String id;
    private String jobTitle;
    private String jobDescription;
    private OfferType offerType;
    private JobMode jobMode;
    private Double stipend;
    private Double ctcPackage;
    private Double minimumCgpa;
    private List<String> jobLocations;
    private LocalDate driveDate;
    private Integer numberOfOpenings;
    private LocalDate applicationDeadline;
    private String selectionProcessDescription;
    private List<String> externalDocLinks;
    private JobStatus jobStatus;
    private Boolean isVerified;
    private CompanyProfileDTO company;

    public static JobProfileDTO from(Job job, Company company) {
        if (job == null)
            return null;
        return JobProfileDTO.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .jobDescription(job.getJobDescription())
                .offerType(job.getOfferType())
                .jobMode(job.getJobMode())
                .stipend(job.getStipend())
                .ctcPackage(job.getCtcPackage())
                .minimumCgpa(job.getMinimumCgpa())
                .jobLocations(job.getJobLocations())
                .driveDate(job.getDriveDate())
                .numberOfOpenings(job.getNumberOfOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .selectionProcessDescription(job.getSelectionProcessDescription())
                .externalDocLinks(job.getExternalDocLinks())
                .jobStatus(job.getJobStatus())
                .isVerified(job.getIsVerified())
                .company(CompanyProfileDTO.from(company))
                .build();
    }

    public static JobProfileDTO from(Job job, CompanyDTO companyDTO) {
        if (job == null)
            return null;
        return JobProfileDTO.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .jobDescription(job.getJobDescription())
                .offerType(job.getOfferType())
                .jobMode(job.getJobMode())
                .stipend(job.getStipend())
                .ctcPackage(job.getCtcPackage())
                .minimumCgpa(job.getMinimumCgpa())
                .jobLocations(job.getJobLocations())
                .driveDate(job.getDriveDate())
                .numberOfOpenings(job.getNumberOfOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .selectionProcessDescription(job.getSelectionProcessDescription())
                .externalDocLinks(job.getExternalDocLinks())
                .jobStatus(job.getJobStatus())
                .isVerified(job.getIsVerified())
                .company(CompanyProfileDTO.from(companyDTO))
                .build();
    }
}
