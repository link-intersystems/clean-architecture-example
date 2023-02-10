package com.link_intersystems.customer;

import com.link_intersystems.lender.Age;

import java.time.*;
import java.util.function.Function;

public class AgeFixture {

    private final Age age;
    private LocalDate birthday;

    public AgeFixture() {
        birthday = LocalDate.of(2000, 5, 17);
        age = new Age(birthday);
    }

    public Age getAge() {
        return age;
    }

    public Clock getClockAtAge(int age) {
        return getBirthdayRelativeClock(birthday -> birthday.plusYears(age));
    }

    private Clock getBirthdayRelativeClock(Function<LocalDateTime, LocalDateTime> timeEditor) {
        LocalDateTime birthDayStartOfDay = birthday.atStartOfDay();
        LocalDateTime editedTime = timeEditor.apply(birthDayStartOfDay);
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset offset = zoneId.getRules().getOffset(editedTime);
        Instant intstant = editedTime.toInstant(offset);
        return Clock.fixed(intstant, zoneId);
    }

    public Clock getClockBeforeAge(int age) {
        return getBirthdayRelativeClock(birthday -> birthday.plusYears(age).minusSeconds(1));
    }
}
