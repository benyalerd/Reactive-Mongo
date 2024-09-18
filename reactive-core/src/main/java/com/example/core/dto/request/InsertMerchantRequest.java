package com.example.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertMerchantRequest {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String email;
    private String tel;
    private String createdBy;
}
