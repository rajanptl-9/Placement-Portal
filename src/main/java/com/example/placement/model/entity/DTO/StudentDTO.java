package com.example.placement.model.entity.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.placement.model.entity.Student;

@Data
@AllArgsConstructor
public class StudentDTO {
    private String id;
    private String rollNo;
    private LocalDate DOB;
    private String firstName;
    private String lastName;
    private String collegeEmail;
    private String personalEmail;
    private String mobile;
    private String department;
    private String highestDegree;
    private String currentYear;
    private Integer yearOfPassing;
    private Double cgpa;
    private String status;

    public static StudentDTO from(Student student) {
        if (student == null)
            return null;

        return new StudentDTO(
                student.getId(),
                student.getRollNo(),
                student.getDOB(),
                student.getFirstName(),
                student.getLastName(),
                student.getCollegeEmail(),
                student.getPersonalEmail(),
                student.getMobile(),
                student.getDepartment(),
                student.getHighestDegree() != null ? student.getHighestDegree().name() : null,
                student.getCurrentYear() != null ? student.getCurrentYear().name() : null,
                student.getYearOfPassing(),
                student.getCgpa(),
                student.getStatus() != null ? student.getStatus().name() : null);
    }
}