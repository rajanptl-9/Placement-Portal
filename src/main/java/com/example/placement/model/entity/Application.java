package com.example.placement.model.entity;

import com.example.placement.model.entity.enums.ApplicationStatus;
import com.example.placement.model.entity.enums.OfferStatus;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "applications")
@CompoundIndex(name = "application_unique_fields", def = "{'jobId':1, 'studentId':1}", unique = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    private String id;

    @Setter(AccessLevel.NONE)
    private String jobId;
    @Setter(AccessLevel.NONE)
    private String studentId;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private ApplicationStatus applicationStatus = ApplicationStatus.APPLIED;
    private OfferStatus offerStatus;

    private String remark;

    // @Builder.Default
    // @Setter(AccessLevel.NONE)
    // private Boolean verifiedByAdmin = false;

    @CreatedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime appliedAt = LocalDateTime.now();
    @LastModifiedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();
}