package com.example.placement.service;

import com.example.placement.model.entity.User;
import com.example.placement.model.entity.Student;
import com.example.placement.model.entity.Company;
import com.example.placement.repository.UserRepository;
import com.example.placement.repository.StudentRepository;
import com.example.placement.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleDataService {
    private static final Logger logger = LoggerFactory.getLogger(SampleDataService.class);

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public SampleDataService(UserRepository userRepository,
                             StudentRepository studentRepository,
                             CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    public void insertSampleUser() {
        User user = new User("Alice", "alice@example.com", 30);
        userRepository.save(user);
        logger.info("Inserted sample User: {}", user);
    }

    public void insertSampleStudent() {
        Student student = new Student("Bob", "bob@student.edu", "Computer Science", 3, 3.8);
        studentRepository.save(student);
        logger.info("Inserted sample Student: {}", student);
    }

    public void insertSampleCompany() {
        Company company = new Company("TechCorp", "Bangalore", "Software", 5000);
        companyRepository.save(company);
        logger.info("Inserted sample Company: {}", company);
    }

    public void insertAll() {
        insertSampleUser();
        insertSampleStudent();
        insertSampleCompany();
    }
}
