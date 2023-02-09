package com.link_intersystems.sakilla.lender;

import java.time.LocalDate;

public class Lender {

    private int id;
    private LocalDate birthday;

    public Lender(int id, LocalDate birthday) {
        this.id = id;
        this.birthday = birthday;
    }

    public Age getAge() {
        return new Age(birthday);
    }
}
