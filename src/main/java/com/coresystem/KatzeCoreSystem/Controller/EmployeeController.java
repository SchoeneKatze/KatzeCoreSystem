package com.coresystem.KatzeCoreSystem.Controller;

import com.coresystem.KatzeCoreSystem.Entities.Employee;
import com.coresystem.KatzeCoreSystem.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // Changed from @RestController for the view method
import org.springframework.ui.Model; // For passing data to Thymeleaf template
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import jakarta.annotation.PostConstruct;

@Controller // This controller will handle both REST API and serve a Thymeleaf view
@RequestMapping("/employees") // Base path for the Thymeleaf view (e.g., /employees)
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Initializes dummy employee data if the 'employees' table is empty.
     * To close this function, only comment out @PostConstruct is enough.
     * change the names of departments and job titles here is good enough.
     */
    @PostConstruct
    public void initDummyData() {
        if (employeeRepository.count() == 0) {
            System.out.println("Generating dummy employee data for MySQL...");
            String[] departments = {"Engineering", "Marketing", "Sales", "HR", "Finance", "Operations", "IT"};
            String[] jobTitles = {"Manager", "Software Engineer", "Analyst", "Specialist", "Coordinator", "Associate"};
            Random random = new Random();

            for (int i = 1; i <= 100; i++) {
                Employee employee = new Employee();
                employee.setEmployeeId(String.format("EMP-%03d", i));
                employee.setFirstName("Employee");
                employee.setLastName(String.valueOf(i));
                employee.setDepartment(departments[random.nextInt(departments.length)]);
                employee.setJobTitle(jobTitles[random.nextInt(jobTitles.length)]);
                employee.setEmail("employee" + i + "@example.com");
                employee.setPhoneNumber("+1-555-0101-" + String.format("%03d", i));
                employee.setHireDate(LocalDate.of(2020 + random.nextInt(5), random.nextInt(12) + 1, random.nextInt(28) + 1));
                employee.setSalary(BigDecimal.valueOf(50000 + random.nextInt(100000)));
                employee.setAddress((random.nextInt(1000) + 1) + " Main St, Anytown, USA");
                employee.setCity("Anytown");
                employee.setStateProvince("CA");
                employee.setZipPostalCode("90210");
                employee.setCountry("USA");
                employee.setDateOfBirth(LocalDate.of(1980 + random.nextInt(20), random.nextInt(12) + 1, random.nextInt(28) + 1));
                employee.setEmergencyContactName("Contact " + i);
                employee.setEmergencyContactPhone("+1-555-0202-" + String.format("%03d", i));
                employee.setStatus("Active");
                employee.setEmploymentType("Full-time");
                employee.setSkills("Java, Spring, SQL, Thymeleaf"); // Updated dummy skill

                employeeRepository.save(employee);
            }
            System.out.println("Dummy employee data generation complete.");
        }
    }

    /**
     * Serves the employee listing page using Thymeleaf.
     * This method will prepare initial data for the view.
     */
    @GetMapping
    public String showEmployeesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String jobTitle,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Specification<Employee> spec = buildEmployeeSpecification(employeeId, name, department, jobTitle);
        Page<Employee> employeesPage = employeeRepository.findAll(spec, pageable);

        // Pass data to the Thymeleaf template
        model.addAttribute("employees", employeesPage.getContent());
        model.addAttribute("currentPage", employeesPage.getNumber() + 1); // 1-indexed for display
        model.addAttribute("totalPages", employeesPage.getTotalPages());
        model.addAttribute("totalItems", employeesPage.getTotalElements());
        model.addAttribute("pageSize", size);

        // Pass back current search/filter terms for form persistence
        model.addAttribute("searchTermId", employeeId);
        model.addAttribute("searchTermName", name);
        model.addAttribute("selectedDepartment", department);
        model.addAttribute("selectedJobTitle", jobTitle);

        // Get unique departments and job titles for dropdowns
        List<String> allDepartments = employeeRepository.findAll(Specification.where(null)).stream()
                .map(Employee::getDepartment)
                .distinct()
                .sorted()
                .toList();
        List<String> allJobTitles = employeeRepository.findAll(Specification.where(null)).stream()
                .map(Employee::getJobTitle)
                .distinct()
                .sorted()
                .toList();

        model.addAttribute("allDepartments", allDepartments);
        model.addAttribute("allJobTitles", allJobTitles);

        return "employees"; // Refers to src/main/resources/templates/employees.html
    }

    /**
     * REST endpoint to get paginated employee data (can be used for AJAX if needed, but not strictly necessary for this Thymeleaf approach).
     * The Thymeleaf page itself is rendered by the `showEmployeesPage` method.
     * I'm keeping this for potential future AJAX use cases, but for this pure Thymeleaf solution, it's not directly called by the frontend.
     */
    @GetMapping("/api") // Changed to /employees/api to avoid conflict with root /employees path
    @ResponseBody // Indicates that the return value should be bound to the web response body
    public ResponseEntity<Page<Employee>> getEmployeesApi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String jobTitle) {

        Pageable pageable = PageRequest.of(page, size);
        Specification<Employee> spec = buildEmployeeSpecification(employeeId, name, department, jobTitle);
        Page<Employee> employees = employeeRepository.findAll(spec, pageable);
        return ResponseEntity.ok(employees);
    }


    /**
     * Retrieves a single employee by their ID. This remains a REST endpoint for detail pop-up.
     */
    @GetMapping("/api/{id}") // Changed to /employees/api/{id}
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Helper method to build specifications
    private Specification<Employee> buildEmployeeSpecification(String employeeId, String name, String department, String jobTitle) {
        Specification<Employee> spec = Specification.where(null);

        if (employeeId != null && !employeeId.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("employeeId")), "%" + employeeId.toLowerCase() + "%"));
        }
        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("lastName")), "%" + name.toLowerCase() + "%")
                    )
            );
        }
        if (department != null && !department.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("department"), department));
        }
        if (jobTitle != null && !jobTitle.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("jobTitle"), jobTitle));
        }
        return spec;
    }
}
