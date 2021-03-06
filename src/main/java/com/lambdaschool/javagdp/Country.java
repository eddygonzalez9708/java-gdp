package com.lambdaschool.javagdp;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Country {
    private @Id @GeneratedValue Long id;
    private String country;
    private Long gdp;

    public Country() {
        // Default Constructor
    }

    public Country(String country, Long gdp) {
        this.country = country;
        this.gdp = gdp;
    }
}
