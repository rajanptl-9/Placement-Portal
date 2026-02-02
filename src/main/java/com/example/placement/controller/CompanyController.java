package com.example.placement.controller;

import com.example.placement.model.entity.Company;
import com.example.placement.model.entity.DTO.CompanyCardDTO;
import com.example.placement.model.entity.DTO.CompanyDTO;
import com.example.placement.model.entity.DTO.CompanyProfileDTO;
import com.example.placement.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<CompanyCardDTO>>> getAllCompanies() {
        return companyService.getAllCompanies().thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<CompanyDTO>> getCompanyById(@PathVariable String id) {
        return companyService.getCompanyById(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping("/{id}/profile")
    public CompletableFuture<ResponseEntity<CompanyProfileDTO>> getCompanyProfile(@PathVariable String id) {
        return companyService.getCompanyProfile(id)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<CompanyDTO>> createCompany(@RequestBody Company company) {
        return companyService.createCompany(company).thenApply(ResponseEntity::ok);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<CompanyDTO>> updateCompany(@PathVariable String id,
            @RequestBody CompanyDTO companyDetails) {
        return companyService.updateCompany(id, companyDetails)
                .thenApply(opt -> opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteCompany(@PathVariable String id) {
        return companyService.deleteCompany(id)
                .thenApply(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }
}
