package org.mas.zoomanagementsystem.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String country;
    private String city;
    private String postalCode;
    private String streetAndHouseNumber;
}