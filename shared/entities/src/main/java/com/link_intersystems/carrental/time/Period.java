package com.link_intersystems.carrental.time;

import java.time.*;
import java.util.Objects;

public class Period {

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

        if (begin.compareTo(thatBegin) <= 0 && end.compareTo(thatBegin) >= 0) {
            return true;
        }

        LocalDateTime thatEnd = period.getEnd();
        return begin.compareTo(thatEnd) <= 0 && end.compareTo(thatEnd) >= 0;
    }

    @Override
    public String
    toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getBegin().toLocalDate());
        sb.append(" ");
        sb.append(getBegin().toLocalTime());

        sb.append(" >> ");

        sb.append(getEnd().toLocalDate());
        sb.append(" ");
        sb.append(getEnd().toLocalTime());

        return sb.toString();
    }

    public int getDays() {
        java.time.Period between = java.time.Period.between(getBegin().toLocalDate(), end.toLocalDate());
        return between.getDays() + 1;
    }

    public boolean isPast(Clock clock) {
        Instant instant = clock.instant();
        ZoneId zoneId = clock.getZone();

        LocalDateTime beginDateTime = getBegin();
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(instant);
        return beginDateTime.toInstant(zoneOffset).isBefore(instant);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(begin, period.begin) && Objects.equals(end, period.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }
}
