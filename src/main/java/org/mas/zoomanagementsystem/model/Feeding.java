package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.EmployeeRole;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Feeding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime feedingDateTime;
    private String quantity; // e.g., "2 kg", "5 units"
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee responsibleEmployee;


    public void setResponsibleEmployee(Employee employee) {
        if (employee == null || !employee.getRoles().contains(EmployeeRole.ZOOKEEPER)) {
            throw new IllegalArgumentException("Employee responsible for feeding must have the ZOOKEEPER role.");
        }
        this.responsibleEmployee = employee;
    }
}