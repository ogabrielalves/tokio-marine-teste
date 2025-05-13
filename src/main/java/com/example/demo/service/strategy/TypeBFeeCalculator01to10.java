package com.example.demo.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TypeBFeeCalculator01to10 implements TaxCalculatorStrategy{

    @Override
    public boolean matches(long daysUntilTransfer) {
        return daysUntilTransfer >= 1 && daysUntilTransfer <= 10;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.valueOf(12);
    }
}
