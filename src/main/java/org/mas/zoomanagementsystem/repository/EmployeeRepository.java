package org.mas.zoomanagementsystem.repository;

import org.mas.zoomanagementsystem.model.Employee;
import org.mas.zoomanagementsystem.model.enums.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an employee by their unique business identifier.
     * @param employeeID The unique ID string of the employee.
     * @return An Optional containing the found employee or empty if not found.
     */
    Optional<Employee> findByEmployeeID(String employeeID);

    /**
     * Finds all employees that have a specific role assigned to them.
     * This is useful for finding all veterinarians, zookeepers, etc.
     * @param role The EmployeeRole to search for.
     * @return A list of employees with the specified role.
     */
    List<Employee> findByRolesContaining(EmployeeRole role);
}
