package com.example.api.controller;

import com.example.api.service.BillPaymentService;
import com.example.core.dto.request.InsertBillPaymentRequest;
import com.example.core.dto.request.UpdatePaymentRequest;
import com.example.core.dto.response.InsertResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bill-payment")
public class BillPaymentController {

    private final BillPaymentService billPaymentService;

    @Operation(summary = "insert bill payment")
    @ApiResponse(responseCode = "200", description = "insert bill payment success", content = @Content(schema = @Schema(implementation = InsertResponse.class)))
    @PostMapping("/insert")
    public Mono<InsertResponse> insertBillPayment(@RequestBody @Validated InsertBillPaymentRequest insertBillPaymentRequest) {
        return billPaymentService.insertBillPayment(insertBillPaymentRequest);
    }

    @Operation(summary = "update bill payment")
    @ApiResponse(responseCode = "200", description = "update bill payment success")
    @PostMapping("/update/payment-status")
    public void updatePaymentStatus(@RequestBody @Validated UpdatePaymentRequest updatePaymentRequest) throws JsonProcessingException {
        billPaymentService.updatePaymentStatus(updatePaymentRequest);
    }

}
