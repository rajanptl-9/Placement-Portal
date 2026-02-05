package com.example.placement.model.entity.DTO;

import com.example.placement.model.entity.Application;
import com.example.placement.model.entity.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCardDTO {
    private String id;
    private String jobId;
    private ApplicationStatus applicationStatus;

    public static ApplicationCardDTO from(Application app) {
        if (app == null)
            return null;
        return ApplicationCardDTO.builder()
                .id(app.getId())
                .jobId(app.getJobId())
                .applicationStatus(app.getApplicationStatus())
                .build();
    }
}
