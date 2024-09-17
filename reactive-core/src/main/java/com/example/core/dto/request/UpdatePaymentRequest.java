package com.example.core.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdatePaymentRequest {
    private List<PaymentDetail> paymentDetailList;

    @Data
    public static class PaymentDetail{
        private String merchantNo;
        private String ref1;
        private String ref2;
    }
}
