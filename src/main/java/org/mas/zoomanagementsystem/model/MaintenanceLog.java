package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.EmployeeRole;
import org.mas.zoomanagementsystem.model.enums.MaintenanceStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MaintenanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logID;
    private LocalDateTime reportedDate;
    private String issueDescription;
    private MaintenanceStatus status;
    private LocalDateTime completionDate;
    private String technicianNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee assignedEmployee;


    public void assignEmployee(Employee employee) {
        if (this.status == MaintenanceStatus.COMPLETED || this.status == MaintenanceStatus.CANCELLED) {
            throw new IllegalStateException("Cannot assign employee to a closed maintenance log.");
        }

        if (employee == null || !employee.getRoles().contains(EmployeeRole.MAINTENANCE_TECHNICIAN)) {
            throw new IllegalArgumentException("Assigned employee must have the MAINTENANCE_TECHNICIAN role.");
        }

        this.assignedEmployee = employee;
        this.status = MaintenanceStatus.ASSIGNED;
    }
}
