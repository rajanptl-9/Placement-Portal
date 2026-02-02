package com.example.placement.service;

import com.example.placement.model.entity.Student;
import com.example.placement.model.entity.DTO.StudentDTO;
import com.example.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Async
    public CompletableFuture<StudentDTO> registerStudent(Student student) {
        try {
            if (studentRepository.findByRollNo(student.getRollNo()).isPresent()) {
                return CompletableFuture
                        .failedFuture(new RuntimeException("Student with this roll number already exists"));
            }
            Student savedStudent = studentRepository.save(student);
            return CompletableFuture.completedFuture(StudentDTO.from(savedStudent));
        } catch (Exception e) {
            System.err.println("Error during student registration: " + e.getMessage());
            return CompletableFuture
                    .failedFuture(new RuntimeException("Error occurred during registration: " + e.getMessage()));
        }
    }

    @Async
    public CompletableFuture<StudentDTO> loginStudent(String rollNo, String password) {
        try {
            StudentDTO dto = studentRepository.findByRollNo(rollNo)
                    .filter(student -> student.getPassword().equals(password))
                    .map(StudentDTO::from)
                    .orElseThrow(() -> new RuntimeException("Invalid roll number or password"));
            return CompletableFuture.completedFuture(dto);
        } catch (Exception e) {
            System.err.println("Error during student login: " + e.getMessage());
            return CompletableFuture
                    .failedFuture(new RuntimeException("Error occurred during login: " + e.getMessage()));
        }
    }

    @Async
    public CompletableFuture<StudentDTO> updateStudentProfile(String id, Student updatedData) {
        try {
            StudentDTO dto = studentRepository.findById(id).map(existingStudent -> {
                if (updatedData.getDOB() != null)
                    existingStudent.setDOB(updatedData.getDOB());
                if (updatedData.getPersonalEmail() != null)
                    existingStudent.setPersonalEmail(updatedData.getPersonalEmail());
                if (updatedData.getMobile() != null)
                    existingStudent.setMobile(updatedData.getMobile());
                if (updatedData.getCity() != null)
                    existingStudent.setCity(updatedData.getCity());
                if (updatedData.getState() != null)
                    existingStudent.setState(updatedData.getState());
                if (updatedData.getPincode() != null)
                    existingStudent.setPincode(updatedData.getPincode());
                if (updatedData.getCurrentYear() != null)
                    existingStudent.setCurrentYear(updatedData.getCurrentYear());
                if (updatedData.getCgpa() != null)
                    existingStudent.setCgpa(updatedData.getCgpa());
                if (updatedData.getLinkedinUrl() != null)
                    existingStudent.setLinkedinUrl(updatedData.getLinkedinUrl());
                if (updatedData.getGithubUrl() != null)
                    existingStudent.setGithubUrl(updatedData.getGithubUrl());
                if (updatedData.getPortfolioUrl() != null)
                    existingStudent.setPortfolioUrl(updatedData.getPortfolioUrl());
                if (updatedData.getResumeLink() != null)
                    existingStudent.setResumeLink(updatedData.getResumeLink());

                return StudentDTO.from(studentRepository.save(existingStudent));
            }).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
            return CompletableFuture.completedFuture(dto);
        } catch (Exception e) {
            System.err.println("Error updating student profile for id " + id + ": " + e.getMessage());
            return CompletableFuture.failedFuture(new RuntimeException("Error updating profile: " + e.getMessage()));
        }
    }
}
