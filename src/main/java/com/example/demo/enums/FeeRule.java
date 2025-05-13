package com.example.demo.enums;

import java.math.BigDecimal;

public enum FeeRule {
    TYPE_A(0, 0, new BigDecimal("3"), new BigDecimal("0.025")),
    TYPE_B(1, 10, new BigDecimal("12"), BigDecimal.ZERO),
    TYPE_C1(11, 20, BigDecimal.ZERO, new BigDecimal("0.082")),
    TYPE_C2(21, 30, BigDecimal.ZERO, new BigDecimal("0.069")),
    TYPE_C3(31, 40, BigDecimal.ZERO, new BigDecimal("0.047")),
    TYPE_C4(41, 50, BigDecimal.ZERO, new BigDecimal("0.017"));

    private final int minDays;
    private final int maxDays;
    private final BigDecimal fixedFee;
    private final BigDecimal percentFee;

    FeeRule(int minDays, int maxDays, BigDecimal fixedFee, BigDecimal percentFee) {
        this.minDays = minDays;
        this.maxDays = maxDays;
        this.fixedFee = fixedFee;
        this.percentFee = percentFee;
    }

    public boolean matches(long days) {
        return days >= minDays && days <= maxDays;
    }

    public BigDecimal calculate(BigDecimal amount) {
        return fixedFee.add(amount.multiply(percentFee));
    }

    public static FeeRule getByDays(long days) {
        for (FeeRule rule : values()) {
            if (rule.matches(days)) {
                return rule;
            }
        }
        throw new IllegalArgumentException("No applicable rate for " + days + " days.");
    }
}
