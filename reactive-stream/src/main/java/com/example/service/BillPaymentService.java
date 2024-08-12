package com.example.service;

import com.example.kafka.dto.payload.BillingPaymentUpdated;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BillPaymentService {

    private final ObjectMapper objectMapper;

    //Send noti to email
    public void latePaymentNotification(String payload) throws JsonProcessingException {
        List<BillingPaymentUpdated> myObjects = objectMapper.readValue(payload, new TypeReference<List<BillingPaymentUpdated>>(){});
    }

}
