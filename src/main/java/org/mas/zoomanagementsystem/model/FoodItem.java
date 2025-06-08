package org.mas.zoomanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Getter
@Setter
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodID;
    private String name;
    private String description;
    @ElementCollection
    private Map<String, Double> nutritionalValue;
    private Double stockQuantity;
    private String unit;


    public void updateStock(double amount) {
        if (this.stockQuantity == null) {
            this.stockQuantity = 0.0;
        }
        this.stockQuantity += amount;

        if (this.stockQuantity < 0) {
             throw new IllegalStateException("Stock quantity cannot be negative.");
        }
    }
}