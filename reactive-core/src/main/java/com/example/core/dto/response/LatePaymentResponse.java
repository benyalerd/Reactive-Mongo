package com.example.core.dto.response;

import lombok.Data;

@Data
public class LatePaymentResponse {
    private String merchantNo;
    private String firstname;
    private String lastname;
    private String email;
    private String tel;
    private Double amount;
}
