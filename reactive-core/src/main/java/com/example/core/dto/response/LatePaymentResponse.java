package com.example.core.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LatePaymentResponse {
    @Schema(name = "merchant no", example = "12345678")
    private String merchantNo;

    @Schema(name = "first name", example = "Waruntorn")
    private String firstname;

    @Schema(name = "last name", example = "Paonil")
    private String lastname;

    @Schema(name = "email", example = "email@emial.com")
    private String email;

    @Schema(name = "tel", example = "0887098766")
    private String tel;

    @Schema(name = "amount", example = "100.0")
    private Double amount;
}
