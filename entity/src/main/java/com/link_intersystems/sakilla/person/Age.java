package com.link_intersystems.sakilla.person;

import java.time.*;

public class Age {

    private LocalDate birthday;

    public Age(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getYears(Clock atTime) {
        Instant now = atTime.instant();
        ZoneId zone = atTime.getZone();
        Period age = Period.between(birthday, LocalDate.ofInstant(now, zone));
        return age.getYears();
    }
}
