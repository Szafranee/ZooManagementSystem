package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.enums.EnclosureType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Enclosure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String enclosureID;
    private String name;
    @Enumerated(EnumType.STRING)
    private EnclosureType type;
    private Double area; // in square meters
    private Integer capacity;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoo_id")
    private Zoo zoo;

    @OneToMany(mappedBy = "enclosure")
    private List<Animal> animals = new ArrayList<>();

    @OneToMany(mappedBy = "enclosure", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipment> equipment = new ArrayList<>();


    public void addAnimalToEnclosure(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null.");
        }
        if (this.capacity != null && this.animals.size() >= this.capacity) {
            throw new IllegalStateException("Enclosure is full. Cannot add more animals.");
        }
        if (!this.animals.contains(animal)) {
            this.animals.add(animal);
            animal.setEnclosure(this);
        }
    }

    public void removeAnimalFromEnclosure(Animal animal) {
        if (animal != null && this.animals.contains(animal)) {
            this.animals.remove(animal);
            animal.setEnclosure(null);
        }
    }
}
