package com.link_intersystems.sakilla.film.listing;

import com.link_intersystems.sakilla.person.Age;

import java.time.LocalDate;

public class Lender {

    private LocalDate birthday;

    public Lender(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Age getAge() {
        return new Age(birthday);
    }
}
