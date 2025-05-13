package com.example.demo.controller;

import com.example.demo.dto.TransferRequest;
import com.example.demo.model.Transfer;
import com.example.demo.service.TransferService;
import com.example.demo.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Transfer>> schedule(@RequestBody @Valid TransferRequest request) {
        return transferService.scheduleTransfer(request);
    }
}
