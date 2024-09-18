package com.example.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class InsertBillPaymentRequest {
    @NotBlank
    @Schema(name = "merchant no", example = "12345678")
    private String merchantNo;

    @NotBlank
    @Schema(name = "reference no. 1", example = "0009")
    private String ref1;

    @NotBlank
    @Schema(name = "reference no. 2", example = "1234")
    private String ref2;

    @NotNull
    @Schema(name = "payment due date", example = "2021-09-17T11:48:06")
    private LocalDateTime dueDate;

    @NotNull
    @Schema(name = "payment status", example = "AWAITING_PAYMENT")
    private PaymentStatus status;

    @Schema(name = "created by", example = "Waruntorn")
    private String createdBy;

    @Schema(name = "amount", example = "100.0")
    private Double amount;
}
