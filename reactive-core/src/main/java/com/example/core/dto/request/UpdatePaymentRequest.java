package com.example.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePaymentRequest {

    @Schema(name = "list payment information")
    private List<PaymentDetail> paymentDetailList;

    @Schema(name = "update by", example = "Waruntorn")
    private String updatedBy;

    @Data
    public static class PaymentDetail{
        @Schema(name = "merchant no", example = "12345678")
        private String merchantNo;

        @Schema(name = "reference no. 1", example = "0009")
        private String ref1;

        @Schema(name = "reference no. 2", example = "1234")
        private String ref2;
    }
}
