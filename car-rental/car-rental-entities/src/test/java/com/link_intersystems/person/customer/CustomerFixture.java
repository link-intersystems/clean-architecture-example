package com.link_intersystems.person.customer;

import com.link_intersystems.EntityFixture;
import com.link_intersystems.time.ClockFactory;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class CustomerFixture extends EntityFixture<Customer> {

    private ClockFactory clockFactory = new ClockFactory();

    @Override
    protected void init(List<Customer> entities) {
        entities.add(createMarySmith());
    }

    private Customer createMarySmith() {
        return new Customer(1, LocalDate.of(2000, 7, 19));
    }

    public Customer getById(Integer customerId) {
        return stream().filter(c -> c.getId() == customerId).findFirst().orElse(null);
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
        return clockFactory.getClock(editedTime);
    }
}
