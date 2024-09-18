package com.example.core.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InsertResponse {

    @Schema(name = "primary key", example = "123456")
    private String id;
}
