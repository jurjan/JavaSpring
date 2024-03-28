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
public final class Plant extends Product {

    private LocalDate pickDate;
    private LocalDate plantDate;
    private LocalDate validTill;
    private String colour;
    private String type;

    public Plant(String title, String description, int qty, float weight, LocalDate plantDate, String colour, String type) {
        super(title, description, qty, weight);
        this.plantDate = plantDate;
        this.colour = colour;
        this.type = type;
    }

    public Plant(String title, String description, int qty, float weight, LocalDate pickDate, LocalDate validTill, String colour, String type) {
        super(title, description, qty, weight);
        this.pickDate = pickDate;
        this.validTill = validTill;
        this.colour = colour;
        this.type = type;
    }

    @Override
    public String toString() {
        return title + ":" + qty;
    }
}
