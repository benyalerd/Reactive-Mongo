package com.example.api.controller;

import com.example.api.service.BillPaymentService;
import com.example.core.dto.request.InsertBillPaymentRequest;
import com.example.core.dto.request.UpdatePaymentRequest;
import com.example.core.dto.response.InsertResponse;
import com.example.core.dto.response.LatePaymentResponse;
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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bill-payment")
public class BillPaymentController {

    private final BillPaymentService billPaymentService;

    @Operation(summary = "create bill payment")
    @ApiResponse(responseCode = "200", description = "insert bill payment success", content = @Content(schema = @Schema(implementation = InsertResponse.class)))
    @PostMapping("/insert")
    public Mono<InsertResponse> insertBillPayment(@RequestBody @Validated InsertBillPaymentRequest insertBillPaymentRequest) {
        return billPaymentService.insertBillPayment(insertBillPaymentRequest);
    }

    @Operation(summary = "daily event -> update bill payment status and get late payment send to kafka after that send email notification to merchant")
    @ApiResponse(responseCode = "200", description = "update bill payment status success")
    @PostMapping("/update/payment-status")
    public Mono<List<LatePaymentResponse>> updatePaymentStatus(@RequestBody @Validated UpdatePaymentRequest updatePaymentRequest) {
        return billPaymentService.updatePaymentStatus(updatePaymentRequest);
    }

}
