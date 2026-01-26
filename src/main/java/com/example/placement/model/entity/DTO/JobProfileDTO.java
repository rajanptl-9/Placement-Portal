package com.example.placement.model.entity.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.placement.model.entity.Job;
import com.example.placement.model.entity.enums.JobMode;
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
    private OfferType offerType;
    private JobMode jobMode;
    private Double stipend;
    private Double ctcPackage;
    private Double minimumCgpa;
    private List<String> jobLocations;
    private Integer numberOfOpenings;
    private LocalDate applicationDeadline;
    private String jobStatus;
    private Boolean isVerified;

    public static JobProfileDTO from(Job job) {
        if (job == null)
            return null;
        return JobProfileDTO.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .offerType(job.getOfferType())
                .jobMode(job.getJobMode())
                .stipend(job.getStipend())
                .ctcPackage(job.getCtcPackage())
                .minimumCgpa(job.getMinimumCgpa())
                .jobLocations(job.getJobLocations())
                .numberOfOpenings(job.getNumberOfOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .jobStatus(job.getJobStatus())
                .isVerified(job.getIsVerified())
                .build();
    }
}
