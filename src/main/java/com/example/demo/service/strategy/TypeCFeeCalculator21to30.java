package com.example.demo.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TypeCFeeCalculator21to30 implements TaxCalculatorStrategy{

    @Override
    public boolean matches(long daysUntilTransfer) {
        return daysUntilTransfer >= 21 && daysUntilTransfer <= 30;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.069));
    }
}
