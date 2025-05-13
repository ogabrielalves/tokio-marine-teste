package com.example.demo.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TypeCFeeCalculator41to50 implements TaxCalculatorStrategy{

    @Override
    public boolean matches(long daysUntilTransfer) {
        return daysUntilTransfer >= 41 && daysUntilTransfer <= 50;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.017));
    }
}
