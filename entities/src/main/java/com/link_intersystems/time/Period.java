package com.link_intersystems.time;

import java.time.*;

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

    public boolean isPast(Clock clock) {
        Instant instant = clock.instant();
        ZoneId zoneId = clock.getZone();

        LocalDateTime beginDateTime = getBegin();
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(instant);
        return beginDateTime.toInstant(zoneOffset).isBefore(instant);
    }
}
