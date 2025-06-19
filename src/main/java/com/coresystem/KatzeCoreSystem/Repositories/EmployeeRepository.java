package com.coresystem.KatzeCoreSystem.Repositories;

import com.coresystem.KatzeCoreSystem.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
    // Spring Data JPA will automatically provide basic CRUD operations.
    // JpaSpecificationExecutor allows for dynamic query building, used in the controller.
}
