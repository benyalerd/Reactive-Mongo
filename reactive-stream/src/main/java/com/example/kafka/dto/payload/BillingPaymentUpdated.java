package com.example.kafka.dto.payload;

import lombok.Data;

@Data
public class BillingPaymentUpdated {
    private String merchantId;
    private String ref1;
    private String ref2;
}
