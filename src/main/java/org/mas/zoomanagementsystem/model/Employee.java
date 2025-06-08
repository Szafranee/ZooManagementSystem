package org.mas.zoomanagementsystem.model;
import jakarta.persistence.*;
import lombok.*;
import org.mas.zoomanagementsystem.model.embeddable.Address;
import org.mas.zoomanagementsystem.model.enums.EmployeeRole;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @Embedded
    private Address address;

    @ElementCollection(targetClass = EmployeeRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    private EnumSet<EmployeeRole> roles = EnumSet.noneOf(EmployeeRole.class);

    // --- Role Specific Fields ---

    // Maintenance Technician
    @ElementCollection
    @CollectionTable(name = "technician_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill")
    private List<String> skills;

    // Groundskeeper
    @ElementCollection
    @CollectionTable(name = "groundskeeper_areas", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "area")
    private List<String> assignedAreas;

    // Zookeeper
    @ManyToMany
    @JoinTable(
            name = "zookeeper_enclosures",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "enclosure_id")
    )
    private List<Enclosure> assignedEnclosures;

    // Veterinarian
    private String licenseNumber;
    private String specialization;
}