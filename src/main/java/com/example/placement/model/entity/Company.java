package com.example.placement.model.entity;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.placement.model.entity.enums.IndustryType;

import java.time.LocalDateTime;

@CompoundIndex(name = "company_unique_fields", def = "{'companyName':1, 'companyEmail':1, 'officialEmail':1, 'officialPhone':1, 'website':1, 'linkedInUrl':1}", unique = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "companies")
public class Company {

    @Id
    private String id;

    /* ================= BASIC DETAILS ================= */
    private String companyName;
    private String companyDescription;
    private IndustryType industryType;

    /* ================= CONTACT DETAILS ================= */
    private String companyEmail;
    private String officialPhone;
    private String website;

    /* ================= ADDRESS ================= */
    private String city;
    private String state;
    @Builder.Default
    private String country = "India";

    /* ================= ONLINE PRESENCE ================= */
    private String linkedInUrl; // LinkedIn URL
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Boolean isVerifiedByTnp = false; // T&P verification flag

    /* ================= METADATA ================= */
    @CreatedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
