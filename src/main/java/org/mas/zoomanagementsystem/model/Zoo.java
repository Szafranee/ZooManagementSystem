package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mas.zoomanagementsystem.model.embeddable.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Zoo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zooName;

    @Embedded
    private Address address;

    @ElementCollection
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "hours")
    @CollectionTable(name = "zoo_opening_hours", joinColumns = @JoinColumn(name = "zoo_id"))
    private Map<String, String> openingHours;

    @OneToMany(mappedBy = "zoo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enclosure> enclosures = new ArrayList<>();
}