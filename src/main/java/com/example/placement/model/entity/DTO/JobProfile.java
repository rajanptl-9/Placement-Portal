package com.example.placement.model.entity.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.placement.model.entity.enums.JobMode;
import com.example.placement.model.entity.enums.OfferType;

public class JobProfile {
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
}
