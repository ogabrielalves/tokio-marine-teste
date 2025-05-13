package com.example.demo.service;

import com.example.demo.dto.TransferRequest;
import com.example.demo.mapper.TransferMapper;
import com.example.demo.model.Transfer;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.strategy.TaxCalculatorStrategy;
import com.example.demo.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final List<TaxCalculatorStrategy> taxStrategies;

    public ResponseEntity<ApiResponse<Transfer>> scheduleTransfer(TransferRequest request) {
        LocalDate today = LocalDate.now();
        LocalDate transferDate = request.getTransferDate();

        long daysUntilTransfer = ChronoUnit.DAYS.between(today, transferDate);

        TaxCalculatorStrategy strategy = taxStrategies.stream()
                .filter(s -> s.matches(daysUntilTransfer))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No applicable rate found for this date"));

        BigDecimal fee = strategy.calculate(request.getAmount());

        Transfer transfer = TransferMapper.toEntity(request, fee);

        transferRepository.save(transfer);

        return ResponseEntity.ok(new ApiResponse<>(200, transfer));
    }
}
