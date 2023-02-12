package com.link_intersystems.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.util.Objects.requireNonNull;

public class Amount {

    private BigDecimal value;

    public Amount(BigDecimal value) {
        this.value = requireNonNull(value)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void round() {

    }

    public BigDecimal getValue() {
        return value;
    }
}
