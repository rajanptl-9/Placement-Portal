package com.example.placement.model.entity.DTO;

import com.example.placement.model.entity.Company;
import com.example.placement.model.entity.enums.IndustryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfileDTO {
    private String companyName;
    private String companyEmail;
    private String companyDescription;
    private IndustryType industryType;
    private String website;
    private String linkedInUrl;
    private Boolean isVerifiedByTnp;

    public static CompanyProfileDTO from(Company company) {
        if (company == null)
            return null;
        return CompanyProfileDTO.builder()
                .companyName(company.getCompanyName())
                .companyEmail(company.getCompanyEmail())
                .companyDescription(company.getCompanyDescription())
                .industryType(company.getIndustryType())
                .website(company.getWebsite())
                .linkedInUrl(company.getLinkedInUrl())
                .isVerifiedByTnp(company.getIsVerifiedByTnp())
                .build();
    }

    public static CompanyProfileDTO from(CompanyDTO companyDTO) {
        if (companyDTO == null)
            return null;
        return CompanyProfileDTO.builder()
                .companyName(companyDTO.getCompanyName())
                .companyEmail(companyDTO.getCompanyEmail())
                .companyDescription(companyDTO.getCompanyDescription())
                .industryType(companyDTO.getIndustryType())
                .website(companyDTO.getWebsite())
                .linkedInUrl(companyDTO.getLinkedInUrl())
                .isVerifiedByTnp(companyDTO.getIsVerifiedByTnp())
                .build();
    }
}
