package com.coursework.courseprojectfxeng.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Shop implements Serializable {
    private List<User> users;
    private List<Product> products;

    public Shop() {
        this.products = new ArrayList<>();
        this.users = new ArrayList<>();
    }
}
