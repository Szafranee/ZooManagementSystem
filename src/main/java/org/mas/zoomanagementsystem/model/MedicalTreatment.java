package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.EmployeeRole;
import org.mas.zoomanagementsystem.model.enums.MedicalTreatmentType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class MedicalTreatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String treatmentID;
    @Enumerated(EnumType.STRING)
    private MedicalTreatmentType type;
    private LocalDateTime date;
    private String diagnosis;
    private String procedureDescription;
    @ElementCollection
    private List<String> medicationAdministered;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee performingEmployee;


    public void setPerformingEmployee(Employee employee) {
        if (employee == null || !employee.getRoles().contains(EmployeeRole.VETERINARIAN)) {
            throw new IllegalArgumentException("Employee performing medical treatment must have the VETERINARIAN role.");
        }
        this.performingEmployee = employee;
    }
}
