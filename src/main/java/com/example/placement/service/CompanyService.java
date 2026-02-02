package com.example.placement.service;

import com.example.placement.model.entity.Company;
import com.example.placement.model.entity.DTO.CompanyCardDTO;
import com.example.placement.model.entity.DTO.CompanyDTO;
import com.example.placement.model.entity.DTO.CompanyProfileDTO;
import com.example.placement.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Async
    public CompletableFuture<List<CompanyCardDTO>> getAllCompanies() {
        try {
            List<CompanyCardDTO> companies = companyRepository.findAll().stream()
                    .map(CompanyCardDTO::from)
                    .collect(Collectors.toList());
            return CompletableFuture.completedFuture(companies);
        } catch (Exception e) {
            System.err.println("Error fetching all companies: " + e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

    @Async
    public CompletableFuture<Optional<CompanyDTO>> getCompanyById(String id) {
        try {
            Optional<CompanyDTO> company = companyRepository.findById(id).map(CompanyDTO::from);
            return CompletableFuture.completedFuture(company);
        } catch (Exception e) {
            System.err.println("Error fetching company by id: " + e.getMessage());
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    @Async
    public CompletableFuture<Optional<CompanyProfileDTO>> getCompanyProfile(String id) {
        try {
            Optional<CompanyProfileDTO> profile = companyRepository.findById(id).map(CompanyProfileDTO::from);
            return CompletableFuture.completedFuture(profile);
        } catch (Exception e) {
            System.err.println("Error fetching company profile: " + e.getMessage());
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    @Async
    public CompletableFuture<CompanyDTO> createCompany(Company company) {
        try {
            Company savedCompany = companyRepository.save(company);
            return CompletableFuture.completedFuture(CompanyDTO.from(savedCompany));
        } catch (Exception e) {
            System.err.println("Error creating company: " + e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Async
    public CompletableFuture<Optional<CompanyDTO>> updateCompany(String id, CompanyDTO companyDetails) {
        try {
            Optional<CompanyDTO> updated = companyRepository.findById(id).map(existingCompany -> {
                if (companyDetails.getCompanyName() != null)
                    existingCompany.setCompanyName(companyDetails.getCompanyName());
                if (companyDetails.getCompanyEmail() != null)
                    existingCompany.setCompanyEmail(companyDetails.getCompanyEmail());
                if (companyDetails.getCompanyDescription() != null)
                    existingCompany.setCompanyDescription(companyDetails.getCompanyDescription());
                if (companyDetails.getIndustryType() != null)
                    existingCompany.setIndustryType(companyDetails.getIndustryType());
                if (companyDetails.getOfficialEmail() != null)
                    existingCompany.setOfficialEmail(companyDetails.getOfficialEmail());
                if (companyDetails.getOfficialPhone() != null)
                    existingCompany.setOfficialPhone(companyDetails.getOfficialPhone());
                if (companyDetails.getWebsite() != null)
                    existingCompany.setWebsite(companyDetails.getWebsite());
                if (companyDetails.getCity() != null)
                    existingCompany.setCity(companyDetails.getCity());
                if (companyDetails.getState() != null)
                    existingCompany.setState(companyDetails.getState());
                if (companyDetails.getCountry() != null)
                    existingCompany.setCountry(companyDetails.getCountry());
                if (companyDetails.getLinkedInUrl() != null)
                    existingCompany.setLinkedInUrl(companyDetails.getLinkedInUrl());
                // Note: isVerifiedByTnp should probably be handled by a separate admin endpoint
                return CompanyDTO.from(companyRepository.save(existingCompany));
            });
            return CompletableFuture.completedFuture(updated);
        } catch (Exception e) {
            System.err.println("Error updating company: " + e.getMessage());
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    @Async
    public CompletableFuture<Boolean> deleteCompany(String id) {
        try {
            if (companyRepository.existsById(id)) {
                companyRepository.deleteById(id);
                return CompletableFuture.completedFuture(true);
            }
            return CompletableFuture.completedFuture(false);
        } catch (Exception e) {
            System.err.println("Error deleting company: " + e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }
}
