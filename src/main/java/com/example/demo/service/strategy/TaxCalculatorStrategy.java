package com.example.demo.service.strategy;

import java.math.BigDecimal;

public interface TaxCalculatorStrategy {
    boolean matches(long daysUntilTransfer);
    BigDecimal calculate(BigDecimal amount);
}
