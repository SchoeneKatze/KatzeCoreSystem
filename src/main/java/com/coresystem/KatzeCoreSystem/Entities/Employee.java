package com.coresystem.KatzeCoreSystem.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees") // Maps to the 'employees' table in MySQL
public class Employee {

    @Id
    @Column(name = "employee_id", length = 50)
    private String employeeId; // Using String for EMP-001 format

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate; // Using LocalDate for date without time

    @Column(name = "salary", precision = 10, scale = 2) // Total 10 digits, 2 after decimal
    private BigDecimal salary;

    @Column(name = "address")
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state_province", length = 100)
    private String stateProvince;

    @Column(name = "zip_postal_code", length = 20)
    private String zipPostalCode;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "emergency_contact_name", length = 255)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 50)
    private String emergencyContactPhone;

    @Column(name = "manager_id", length = 50)
    private String managerId; // References another employee's ID

    @Column(name = "status", length = 50)
    private String status; // e.g., 'Active', 'On Leave', 'Terminated'

    @Column(name = "employment_type", length = 50)
    private String employmentType; // e.g., 'Full-time', 'Part-time', 'Contractor'

    @Column(name = "last_performance_review_date")
    private LocalDate lastPerformanceReviewDate;

    @Column(name = "skills", columnDefinition = "TEXT") // TEXT for longer strings
    private String skills;


    // --- Constructors ---
    public Employee() {
    }

    public Employee(String employeeId, String firstName, String lastName, String department, String jobTitle,
                    String email, String phoneNumber, LocalDate hireDate, BigDecimal salary, String address,
                    String city, String stateProvince, String zipPostalCode, String country, LocalDate dateOfBirth,
                    String emergencyContactName, String emergencyContactPhone, String managerId, String status,
                    String employmentType, LocalDate lastPerformanceReviewDate, String skills) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.jobTitle = jobTitle;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
        this.address = address;
        this.city = city;
        this.stateProvince = stateProvince;
        this.zipPostalCode = zipPostalCode;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.managerId = managerId;
        this.status = status;
        this.employmentType = employmentType;
        this.lastPerformanceReviewDate = lastPerformanceReviewDate;
        this.skills = skills;
    }

    // --- Getters and Setters ---
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDate getLastPerformanceReviewDate() {
        return lastPerformanceReviewDate;
    }

    public void setLastPerformanceReviewDate(LocalDate lastPerformanceReviewDate) {
        this.lastPerformanceReviewDate = lastPerformanceReviewDate;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
