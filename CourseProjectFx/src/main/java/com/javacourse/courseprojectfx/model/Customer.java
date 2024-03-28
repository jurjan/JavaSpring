package com.javacourse.courseprojectfx.model;

import jakarta.persistence.Access;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends User {
    private String cardNo;
    private String deliveryAddress;
    private String billingAddress;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cart> myCarts = new ArrayList<>();

    public Customer(String name, String surname, String login, String password, String cardNo, String deliveryAddress, String billingAddress, LocalDate birthDate) {
        super(name, surname, login, password);
        this.cardNo = cardNo;
        this.deliveryAddress = deliveryAddress;
        this.billingAddress = billingAddress;
        this.birthDate = birthDate;
    }

    public Customer(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    @Override
    public String toString() {
        return "login: " + login;
    }
}
