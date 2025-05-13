package com.example.demo.controller;

import com.example.demo.documentation.TransferControllerDoc;
import com.example.demo.dto.TransferRequest;
import com.example.demo.model.Transfer;
import com.example.demo.service.TransferService;
import com.example.demo.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController implements TransferControllerDoc {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Override
    public ResponseEntity<ApiResponse<Transfer>> schedule(@RequestBody @Valid TransferRequest request) {
        return transferService.scheduleTransfer(request);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Transfer>>> listAll() {
        return transferService.listAllTransfers();
    }
}
