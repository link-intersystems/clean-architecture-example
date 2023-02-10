package com.link_intersystems.person.customer;

import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class CustomerFixture {

    private List<Customer> customers = new ArrayList<>();

    public CustomerFixture() {
        customers.add(createMarySmith());
    }

    private Customer createMarySmith() {
        return new Customer(1, LocalDate.of(2000, 7, 19));
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public Customer getById(Integer customerId) {
        return getCustomers().stream().filter(c -> c.getId() == customerId).findFirst().orElse(null);
    }

    public Clock getClockAtAge(LocalDate birthday, int age) {
        return getBirthdayRelativeClock(birthday, b -> b.plusYears(age));
    }


    public Clock getClockBeforeAge(LocalDate birthday, int age) {
        return getBirthdayRelativeClock(birthday, b -> b.plusYears(age).minusSeconds(1));
    }

    private Clock getBirthdayRelativeClock(LocalDate birthday, Function<LocalDateTime, LocalDateTime> timeEditor) {
        LocalDateTime birthDayStartOfDay = birthday.atStartOfDay();
        LocalDateTime editedTime = timeEditor.apply(birthDayStartOfDay);
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset offset = zoneId.getRules().getOffset(editedTime);
        Instant intstant = editedTime.toInstant(offset);
        return Clock.fixed(intstant, zoneId);
    }
}
