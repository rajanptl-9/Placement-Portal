package com.example.placement;

import com.example.placement.model.entity.*;
import com.example.placement.model.entity.enums.*;
import com.example.placement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class CollegePlacementPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollegePlacementPortalApplication.class, args);
        System.out.println("Application started successfully");
    }

    @Bean
    public CommandLineRunner demo(StudentRepository studentRepository,
                                  CompanyRepository companyRepository,
                                  JobRepository jobRepository,
                                  AdminRepository adminRepository,
                                  ApplicationRepository applicationRepository) {
        return (args) -> {
            // 1. Create and Save Student
            Student student = Student.builder()
                    .firstName("Rahul")
                    .lastName("Kumar")
                    .rollNo("CSE-2024-001")
                    .DOB(LocalDate.of(2002, 5, 15))
                    // .citizenship("Indian") // Using default "Indian"
                    .collegeEmail("rahul.k@college.edu")
                    .personalEmail("rahul.k@gmail.com")
                    .mobile("9876543210")
                    .city("Mumbai")
                    .state("Maharashtra")
                    .pincode("400001")
                    .department("CSE")
                    // .collegeName(...) // Using default "National Institute of Technology, Goa"
                    .highestDegree(Degree.BTECH)
                    .currentYear(CurrentYear.IV)
                    .yearOfPassing(2024)
                    .cgpa(8.5)
                    .linkedinUrl("https://linkedin.com/in/rahulkumar")
                    .githubUrl("https://github.com/rahulkumar")
                    .portfolioUrl("https://rahulkumar.dev")
                    // .status(AccountStatus.ACTIVE) // Using default ACTIVE
                    .resumeLink("https://drive.google.com/file/d/sample-resume")
                    .panNumber("ABCDE1234F")
                    .aadhaarNumber("123456789012")
                    .password("hashed_password_123")
                    .build();

            studentRepository.save(student);

            // 2. Create and Save Company
            Company company = Company.builder()
                    .companyName("TechCorp Solutions")
                    .companyEmail("info@techcorp.com")
                    .companyDescription("Leading provider of enterprise software solutions.")
                    .industryType(IndustryType.SOFTWARE)
                    .officialEmail("hr@techcorp.com")
                    .officialPhone("022-12345678")
                    .website("https://techcorp.com")
                    .city("Bangalore")
                    .state("Karnataka")
                    // .country("India") // Using default "India"
                    .linkedInUrl("https://linkedin.com/company/techcorp")
                    // .isVerifiedByTnp(false) // Using default false
                    .build();

            company = companyRepository.save(company);

            // 3. Create and Save Job
            Job job = Job.builder()
                    .jobTitle("Software Engineer Intern")
                    .jobDescription("Backend development role using Java and Spring Boot.")
                    .companyId(company.getId()) // Restricted @Setter field, but Builder works
                    .hrEmail("hr@techcorp.com")
                    .hrMobileNo("9876500000")
                    .offerType(OfferType.INTERN)
                    .jobMode(JobMode.HYBRID)
                    .driveMode(DriveMode.ONLINE)
                    .stipend(25000.0)
                    .ctcPackage(0.0)
                    .minimumCgpa(7.5)
                    .branchesAllowed(List.of("CSE", "IT"))
                    .eligibleDegree(List.of(EligibleDegree.BTECH))
                    .graduatingYearBtech(2024)
                    .graduatingYearMtech(null)
                    .jobLocations(List.of("Bangalore", "Remote"))
                    .driveDate(LocalDate.now().plusDays(10))
                    .numberOfOpenings(5)
                    .applicationDeadline(LocalDate.now().plusDays(5))
                    .selectionProcessDescription("Online Test -> Technical Interview -> HR Interview")
                    .upcomingEvent(UpcomingEvent.SHORTLISTING)
                    .externalDocLinks(List.of("https://techcorp.com/careers/jd"))
                    // .jobStatus("OPEN") // Using default "OPEN"
                    // .isVerified(false) // Using default false
                    .build();

            jobRepository.save(job);

            // 4. Create and Save Admin
            Admin admin = Admin.builder()
                    .name("Super Admin")
                    .email("admin@college.edu")
                    .username("superadmin")
                    .mobile("9999999999")
                    .password("hashed_admin_password")
                    .role(AdminRole.SUPER_ADMIN)
                    .isActive(true)
                    .lastLoginAt(LocalDateTime.now())
                    .build();
            
            adminRepository.save(admin);

            // 5. Create and Save Application
            Application application = Application.builder()
                    .jobId(job.getId())
                    .studentId(student.getId())
                    // .applicationStatus(...) // Using default APPLIED
                    .offerStatus(OfferStatus.NOT_OFFERED)
                    .remark("Applied via portal")
                    // .verifiedByAdmin(false) // Using default false
                    .build();

            applicationRepository.save(application);

            // 6. Print Results
            System.out.println("--------------------------------------");
            System.out.println("✅ Student Object Created & Saved:");
            System.out.println("ID: " + student.getId());
            System.out.println("Full Name: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("Roll No: " + student.getRollNo());
            System.out.println("DOB: " + student.getDOB());
            System.out.println("Citizenship: " + student.getCitizenship());
            System.out.println("Emails: " + student.getCollegeEmail() + " | " + student.getPersonalEmail());
            System.out.println("Mobile: " + student.getMobile());
            System.out.println("Address: " + student.getCity() + ", " + student.getState() + " - " + student.getPincode());
            System.out.println("Education: " + student.getDepartment() + " at " + student.getCollegeName());
            System.out.println("Degree: " + student.getHighestDegree() + " | Year: " + student.getCurrentYear() + " | Passing: " + student.getYearOfPassing());
            System.out.println("Performance: CGPA " + student.getCgpa());
            System.out.println("Social: LinkedIn(" + student.getLinkedinUrl() + "), GitHub(" + student.getGithubUrl() + "), Portfolio(" + student.getPortfolioUrl() + ")");
            System.out.println("Identity: PAN(" + student.getPanNumber() + "), Aadhaar(" + student.getAadhaarNumber() + ")");
            System.out.println("Account: Status(" + student.getStatus() + ") | Password(" + student.getPassword() + ")");
            System.out.println("Audit: Created(" + student.getCreatedAt() + "), Updated(" + student.getUpdatedAt() + ")");
            System.out.println("--------------------------------------");

            System.out.println("✅ Company Object Created & Saved:");
            System.out.println("ID: " + company.getId());
            System.out.println("Name: " + company.getCompanyName());
            System.out.println("Email: " + company.getCompanyEmail());
            System.out.println("Industry: " + company.getIndustryType());
            System.out.println("Description: " + company.getCompanyDescription());
            System.out.println("Contacts: Email(" + company.getOfficialEmail() + "), Phone(" + company.getOfficialPhone() + ")");
            System.out.println("Website: " + company.getWebsite());
            System.out.println("Location: " + company.getCity() + ", " + company.getState() + ", " + company.getCountry());
            System.out.println("Presence: LinkedIn URL(" + company.getLinkedInUrl() + ")");
            System.out.println("Verification: T&P Verified(" + company.getIsVerifiedByTnp() + ")");
            System.out.println("Audit: Created(" + company.getCreatedAt() + "), Updated(" + company.getUpdatedAt() + ")");
            System.out.println("--------------------------------------");

            System.out.println("✅ Job Object Created & Saved:");
            System.out.println("ID: " + job.getId());
            System.out.println("Title: " + job.getJobTitle());
            System.out.println("Description: " + job.getJobDescription());
            System.out.println("Company Link ID: " + job.getCompanyId());
            System.out.println("HR Contacts: Email(" + job.getHrEmail() + "), Mobile(" + job.getHrMobileNo() + ")");
            System.out.println("Type & Mode: Offer(" + job.getOfferType() + "), Job(" + job.getJobMode() + "), Drive(" + job.getDriveMode() + ")");
            System.out.println("Compensation: Stipend(" + job.getStipend() + "), CTC(" + job.getCtcPackage() + ")");
            System.out.println("Eligibility: Min CGPA(" + job.getMinimumCgpa() + "), Branches(" + job.getBranchesAllowed() + "), Degrees(" + job.getEligibleDegree() + ")");
            System.out.println("Graduating Years: BTech(" + job.getGraduatingYearBtech() + "), MTech(" + job.getGraduatingYearMtech() + ")");
            System.out.println("Details: Locations(" + job.getJobLocations() + "), Drive Date(" + job.getDriveDate() + "), Openings(" + job.getNumberOfOpenings() + ")");
            System.out.println("Process: Deadline(" + job.getApplicationDeadline() + "), Upcoming Event(" + job.getUpcomingEvent() + ")");
            System.out.println("Selection: " + job.getSelectionProcessDescription());
            System.out.println("Docs: " + job.getExternalDocLinks());
            System.out.println("Status: Job Status(" + job.getJobStatus() + ") | Verified(" + job.getIsVerified() + ")");
            System.out.println("Audit: Created(" + job.getCreatedAt() + "), Updated(" + job.getUpdatedAt() + ")");
            System.out.println("--------------------------------------");

            System.out.println("✅ Admin Object Created & Saved:");
            System.out.println("ID: " + admin.getId());
            System.out.println("Name: " + admin.getName());
            System.out.println("Account: Username(" + admin.getUsername() + "), Email(" + admin.getEmail() + "), Mobile(" + admin.getMobile() + ")");
            System.out.println("Auth: Password(" + admin.getPassword() + "), Role(" + admin.getRole() + "), Active(" + admin.getIsActive() + ")");
            System.out.println("Audit: Last Login(" + admin.getLastLoginAt() + "), Created(" + admin.getCreatedAt() + "), Updated(" + admin.getUpdatedAt() + ")");
            System.out.println("--------------------------------------");

            System.out.println("✅ Application Object Created & Saved:");
            System.out.println("ID: " + application.getId());
            System.out.println("Links: Job ID(" + application.getJobId() + "), Student ID(" + application.getStudentId() + ")");
            System.out.println("Status: Application(" + application.getApplicationStatus() + "), Offer(" + application.getOfferStatus() + ")");
            System.out.println("Details: Remark(" + application.getRemark() + "), Admin Verified(" + application.getVerifiedByAdmin() + ")");
            System.out.println("Audit: Applied at(" + application.getAppliedAt() + "), Updated at(" + application.getUpdatedAt() + ")");
            System.out.println("--------------------------------------");
        };
    }
}
