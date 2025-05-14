package com.example.demo.mapper;

import com.example.demo.dto.TransferRequest;
import com.example.demo.model.Transfer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferMapper {
    public static Transfer toEntity(TransferRequest dto, BigDecimal fee) {
        Transfer transfer = new Transfer();

        transfer.setOriginAccount(dto.getOriginAccount());
        transfer.setDestinationAccount(dto.getDestinationAccount());
        transfer.setAmount(dto.getAmount());
        transfer.setFee(fee);
        transfer.setTransferDate(dto.getTransferDate());
        transfer.setSchedulingDate(LocalDate.now());

        return transfer;
    }
}
