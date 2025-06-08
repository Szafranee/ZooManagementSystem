package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.EquipmentStatus;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String equipmentID;
    private String name;
    private String type;
    private LocalDate installationDate;
    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enclosure_id")
    private Enclosure enclosure;


    public void updateStatus(EquipmentStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = newStatus;
    }
}
