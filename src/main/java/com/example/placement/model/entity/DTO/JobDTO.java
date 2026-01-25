package com.example.placement.model.entity.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.placement.model.entity.enums.Branch;
import com.example.placement.model.entity.enums.DriveMode;
import com.example.placement.model.entity.enums.EligibleDegree;
import com.example.placement.model.entity.enums.JobMode;
import com.example.placement.model.entity.enums.OfferType;
import com.example.placement.model.entity.enums.UpcomingEvent;

public class JobDTO {
    private String id;
    private String jobTitle;
    private String jobDescription;
    private String companyId;
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
    private String jobStatus;
    private Boolean isVerified;
}
