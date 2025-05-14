package com.example.demo.documentation;

import com.example.demo.dto.TransferRequest;
import com.example.demo.model.Transfer;
import com.example.demo.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Transfers", description = "Endpoints related to scheduling and listing transfers")
@RequestMapping("/transfers")
public interface TransferControllerDoc {

    @Operation(
            summary = "Schedule a new transfer",
            description = "Schedules a transfer between two accounts with the specified amount and date"
    )
    @PostMapping
    ResponseEntity<ApiResponse<Transfer>> schedule(
            @Parameter(description = "Transfer request payload", required = true)
            @RequestBody @Valid TransferRequest request
    );

    @Operation(
            summary = "List all transfers",
            description = "Retrieves a list of all scheduled transfers"
    )
    @GetMapping
    ResponseEntity<ApiResponse<List<Transfer>>> listAll();
}