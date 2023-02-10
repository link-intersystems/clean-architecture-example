package com.link_intersystems.person.customer;

import java.time.*;
import java.util.function.Function;

public class CustomerFixture {

    private final Customer customer;
    private LocalDate birthday;

    public CustomerFixture() {
        birthday = LocalDate.of(2000, 7, 19);
        customer = new Customer(1, birthday);
    }

    public Customer getCustomer() {
        return customer;
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
