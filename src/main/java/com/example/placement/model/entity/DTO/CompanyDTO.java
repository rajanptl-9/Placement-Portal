package com.example.placement.model.entity.DTO;

import com.example.placement.model.entity.Company;
import com.example.placement.model.entity.enums.IndustryType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDTO {
    private String id;
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private IndustryType industryType;
    private String officialEmail;
    private String officialPhone;
    private String website;
    private String city;
    private String state;
    private String country;
    private String linkedInUrl;
    private Boolean isVerifiedByTnp;

    public static CompanyDTO from(Company company) {
        if (company == null)
            return null;
        return CompanyDTO.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .companyEmail(company.getCompanyEmail())
                .companyDescription(company.getCompanyDescription())
                .industryType(company.getIndustryType())
                .officialEmail(company.getOfficialEmail())
                .officialPhone(company.getOfficialPhone())
                .website(company.getWebsite())
                .city(company.getCity())
                .state(company.getState())
                .country(company.getCountry())
                .linkedInUrl(company.getLinkedInUrl())
                .isVerifiedByTnp(company.getIsVerifiedByTnp())
                .build();
    }
}
