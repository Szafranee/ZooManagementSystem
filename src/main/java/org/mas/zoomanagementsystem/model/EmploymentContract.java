package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.ShiftType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class EmploymentContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contractID;
    private LocalDate startDate;
    private LocalDate endDate;
    private String positionTitle;
    private BigDecimal salary;
    private String terms;
    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoo_id")
    private Zoo zoo;
}