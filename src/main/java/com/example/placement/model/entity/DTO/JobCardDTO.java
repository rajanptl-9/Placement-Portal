package com.example.placement.model.entity.DTO;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.Company;
import com.example.placement.model.entity.enums.JobMode;
import com.example.placement.model.entity.enums.OfferType;

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
public class JobCardDTO {
    private String id;
    private String jobTitle;
    private OfferType offerType;
    private JobMode jobMode;
    private Double ctcPackage;
    private List<String> jobLocations;
    private LocalDate applicationDeadline;
    private CompanyCardDTO company;

    public static JobCardDTO from(Job job, Company company) {
        if (job == null)
            return null;
        return JobCardDTO.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .offerType(job.getOfferType())
                .jobMode(job.getJobMode())
                .ctcPackage(job.getCtcPackage())
                .jobLocations(job.getJobLocations())
                .applicationDeadline(job.getApplicationDeadline())
                .company(CompanyCardDTO.from(company))
                .build();
    }

    public static JobCardDTO from(Job job, CompanyDTO companyDTO) {
        if (job == null)
            return null;
        return JobCardDTO.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .offerType(job.getOfferType())
                .jobMode(job.getJobMode())
                .ctcPackage(job.getCtcPackage())
                .jobLocations(job.getJobLocations())
                .applicationDeadline(job.getApplicationDeadline())
                .company(CompanyCardDTO.from(companyDTO))
                .build();
    }
}
