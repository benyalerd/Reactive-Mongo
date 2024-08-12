package com.example.core.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsertBillPaymentRequest {
    @NotNull
    private String merchantNo;
    @NotNull
    private String ref1;
    @NotNull
    private String ref2;
    @NotNull
    private String dueDate;
    @NotNull
    private PaymentStatus status;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private Double amount;
}
