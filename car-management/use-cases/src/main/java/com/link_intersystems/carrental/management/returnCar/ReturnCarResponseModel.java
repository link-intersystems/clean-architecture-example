package com.link_intersystems.carrental.management.returnCar;

import java.math.BigDecimal;

public class ReturnCarResponseModel {
    private BigDecimal totalAmount;

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
