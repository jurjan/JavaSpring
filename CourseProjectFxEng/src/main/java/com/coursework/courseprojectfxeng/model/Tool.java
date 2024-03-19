package com.coursework.courseprojectfxeng.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tool extends Product {
    private String manufacturer;

    public Tool(String title, String description, int qty, float weight, String manufacturer) {
        super(title, description, qty, weight);
        this.manufacturer = manufacturer;
    }
}
