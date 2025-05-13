package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotBlank(message = "Source account is required")
    @Pattern(regexp = "\\d{10}", message = "Source account must have 10 digits")
    private String originAccount;

    @NotBlank(message = "Destination account is required")
    @Pattern(regexp = "\\d{10}", message = "Destination account must have 10 digits")
    private String destinationAccount;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Transfer date is required")
    @FutureOrPresent(message = "Transfer date must be today or in the future")
    private LocalDate transferDate;
}
