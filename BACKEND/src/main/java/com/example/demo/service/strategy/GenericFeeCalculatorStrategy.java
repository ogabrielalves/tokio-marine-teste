package com.example.demo.service.strategy;

import com.example.demo.enums.FeeRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GenericFeeCalculatorStrategy implements TaxCalculatorStrategy {

    private FeeRule currentRule;

    @Override
    public boolean matches(long daysUntilTransfer) {
        try {
            this.currentRule = FeeRule.getByDays(daysUntilTransfer);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        if (currentRule == null) {
            throw new IllegalStateException("Rate rule has not been defined. Make sure to call matches() first.");
        }
        return currentRule.calculate(amount);
    }
}
