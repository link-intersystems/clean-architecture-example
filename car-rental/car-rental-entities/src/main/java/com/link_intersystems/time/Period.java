package com.link_intersystems.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Period {

    public static final Period INFINITE = new Period(LocalDate.MIN.atStartOfDay(), LocalDate.MAX.atTime(23, 59, 59, 999_999_999));

    private LocalDateTime begin;
    private LocalDateTime end;

    public Period(LocalDateTime begin, LocalDateTime end) {
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("begin must be before end");
        }
        this.begin = begin;
        this.end = end;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean overlaps(Period period) {
        if (includes(period)) return true;

        return period.includes(this);
    }

    private boolean includes(Period period) {
        LocalDateTime thatBegin = period.getBegin();

        if (begin.isBefore(thatBegin) && end.isAfter(thatBegin)) {
            return true;
        }

        LocalDateTime thatEnd = period.getEnd();
        return begin.isBefore(thatEnd) && end.isAfter(thatEnd);
    }

    @Override
    public String
    toString() {
        return "Period{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }

    public int getDays() {
        java.time.Period between = java.time.Period.between(getBegin().toLocalDate(), end.toLocalDate());
        return between.getDays() + 1;
    }
}
