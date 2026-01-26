package com.example.placement.service;

import com.example.placement.model.entity.Student;
import com.example.placement.model.entity.DTO.StudentDTO;
import com.example.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO registerStudent(Student student) {
        try {
            if (studentRepository.findByRollNo(student.getRollNo()).isPresent()) {
                throw new RuntimeException("Student with this roll number already exists");
            }
            return StudentDTO.from(studentRepository.save(student));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during registration: " + e.getMessage());
        }
    }

    public StudentDTO loginStudent(String rollNo, String password) {
        try {
            return studentRepository.findByRollNo(rollNo)
                    .filter(student -> student.getPassword().equals(password))
                    .map(StudentDTO::from)
                    .orElseThrow(() -> new RuntimeException("Invalid roll number or password"));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during login: " + e.getMessage());
        }
    }

    public StudentDTO updateStudentProfile(String id, Student updatedData) {
        try {
            return studentRepository.findById(id).map(existingStudent -> {
                // if (updatedData.getFirstName() != null)
                // existingStudent.setFirstName(updatedData.getFirstName());
                // if (updatedData.getLastName() != null)
                // existingStudent.setLastName(updatedData.getLastName());
                if (updatedData.getDOB() != null)
                    existingStudent.setDOB(updatedData.getDOB());
                // if (updatedData.getCitizenship() != null)
                // existingStudent.setCitizenship(updatedData.getCitizenship());
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
                // if (updatedData.getDepartment() != null)
                // existingStudent.setDepartment(updatedData.getDepartment());
                // if (updatedData.getHighestDegree() != null)
                // existingStudent.setHighestDegree(updatedData.getHighestDegree());
                if (updatedData.getCurrentYear() != null)
                    existingStudent.setCurrentYear(updatedData.getCurrentYear());
                // if (updatedData.getYearOfPassing() != null)
                // existingStudent.setYearOfPassing(updatedData.getYearOfPassing());
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
                // if (updatedData.getPanNumber() != null)
                // existingStudent.setPanNumber(updatedData.getPanNumber());
                // if (updatedData.getAadhaarNumber() != null)
                // existingStudent.setAadhaarNumber(updatedData.getAadhaarNumber());

                return StudentDTO.from(studentRepository.save(existingStudent));
            }).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error updating profile: " + e.getMessage());
        }
    }
}
