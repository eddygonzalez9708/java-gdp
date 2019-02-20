package com.lambdaschool.javagdp;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String name) {
        super("Could not find the country " + name + ".");
    }
}
