package com.link_intersystems.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Amount {

    private BigDecimal value;

    public Amount(String value) {
        this(new BigDecimal(value));
    }

    public Amount(BigDecimal value) {
        this.value = requireNonNull(value)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Amount multiply(int multiplier) {
        return new Amount(getValue().multiply(new BigDecimal(multiplier)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return Objects.equals(value, amount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
