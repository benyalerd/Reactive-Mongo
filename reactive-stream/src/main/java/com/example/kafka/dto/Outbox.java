package com.example.kafka.dto;

import lombok.Data;

@Data
public class Outbox {
    private String id;
    private String type;
    private String payload;
}
