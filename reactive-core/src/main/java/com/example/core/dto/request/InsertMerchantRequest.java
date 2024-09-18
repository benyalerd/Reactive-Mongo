package com.example.core.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertMerchantRequest {
    @NotBlank
    @Schema(name = "first name", example = "Waruntorn")
    private String firstname;

    @NotBlank
    @Schema(name = "last name", example = "Paonil")
    private String lastname;

    @NotBlank
    @Schema(name = "email", example = "email@emial.com")
    private String email;

    @Schema(name = "tel", example = "0887098766")
    private String tel;

    @Schema(name = "created by", example = "Waruntorn")
    private String createdBy;
}
