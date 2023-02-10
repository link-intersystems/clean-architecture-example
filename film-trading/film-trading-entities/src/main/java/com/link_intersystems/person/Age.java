package com.link_intersystems.person;

public class Age {

    private int years;

    public Age(int years) {
        if (years < 0) {
            throw new IllegalArgumentException("An age can not be less then 0");
        }

        this.years = years;
    }

    public int getYears() {
        return years;
    }
}
