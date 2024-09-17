package com.example.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class InsertBillPaymentRequest {
    @NotBlank
    private String merchantNo;
    @NotBlank
    private String ref1;
    @NotBlank
    private String ref2;
    @NotNull
    private LocalDateTime dueDate;
    @NotNull
    private PaymentStatus status;
    private String createdBy;
    private Double amount;
}
