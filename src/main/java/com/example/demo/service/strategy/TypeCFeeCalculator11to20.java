package com.example.demo.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TypeCFeeCalculator11to20 implements TaxCalculatorStrategy {

    @Override
    public boolean matches(long daysUntilTransfer) {
        return daysUntilTransfer >= 11 && daysUntilTransfer <= 20;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.082));
    }
}
