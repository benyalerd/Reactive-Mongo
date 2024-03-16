package com.example.core.dto.request;

import lombok.Data;

@Data
public class AccountRequest {
    private String id;
    private String owner;
    private Double value;
}
