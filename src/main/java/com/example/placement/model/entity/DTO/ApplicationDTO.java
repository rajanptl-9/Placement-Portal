package com.example.placement.model.entity.DTO;

import com.example.placement.model.entity.enums.ApplicationStatus;
import com.example.placement.model.entity.enums.OfferStatus;

public class ApplicationDTO {
    private String id;
    private String jobId;
    private String studentId;
    private ApplicationStatus applicationStatus;
    private OfferStatus offerStatus;
    private String remark;
}
