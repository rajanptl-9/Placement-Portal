package com.example.placement.model.entity.DTO;

import com.example.placement.model.entity.Application;
import com.example.placement.model.entity.enums.ApplicationStatus;
import com.example.placement.model.entity.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
    private String id;
    private String jobId;
    private String jobTitle;
    private String companyName;
    private String studentId;
    private ApplicationStatus applicationStatus;
    private OfferStatus offerStatus;
    private String remark;
    private LocalDateTime appliedAt;

    public static ApplicationResponseDTO from(Application app, String jobTitle, String companyName) {
        if (app == null)
            return null;
        return ApplicationResponseDTO.builder()
                .id(app.getId())
                .jobId(app.getJobId())
                .jobTitle(jobTitle)
                .companyName(companyName)
                .studentId(app.getStudentId())
                .applicationStatus(app.getApplicationStatus())
                .offerStatus(app.getOfferStatus())
                .remark(app.getRemark())
                .appliedAt(app.getAppliedAt())
                .build();
    }
}
