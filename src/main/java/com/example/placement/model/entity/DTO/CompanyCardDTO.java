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
public class CompanyCardDTO {
    private String companyName;
    private IndustryType industryType;
    private String city;
    private String website;

    public static CompanyCardDTO from(Company company) {
        if (company == null)
            return null;
        return CompanyCardDTO.builder()
                .companyName(company.getCompanyName())
                .industryType(company.getIndustryType())
                .city(company.getCity())
                .website(company.getWebsite())
                .build();
    }
}
