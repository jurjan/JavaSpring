package com.javacourse.courseprojectfx.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seed extends Product {
    private String farm;
    private LocalDate collectDate;
    private SeedType seedType;

    public Seed(String title, String description, int qty, float weight, String farm, LocalDate collectDate, SeedType seedType) {
        super(title, description, qty, weight);
        this.farm = farm;
        this.collectDate = collectDate;
        this.seedType = seedType;
    }
}
