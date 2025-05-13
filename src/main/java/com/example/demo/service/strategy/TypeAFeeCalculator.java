package com.example.demo.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TypeAFeeCalculator implements TaxCalculatorStrategy {

    @Override
    public boolean matches(long daysUntilTransfer) {
        return daysUntilTransfer == 0;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.valueOf(3).add(amount.multiply(BigDecimal.valueOf(0.025)));
    }
}
