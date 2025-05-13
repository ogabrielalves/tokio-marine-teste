package com.example.demo.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TypeCFeeCalculator31to40 implements TaxCalculatorStrategy{

    @Override
    public boolean matches(long daysUntilTransfer) {
        return daysUntilTransfer >= 31 && daysUntilTransfer <= 40;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.047));
    }
}
