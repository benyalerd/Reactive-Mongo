package com.example.service;

import com.example.core.dto.request.UpdatePaymentRequest;
import com.example.core.dto.response.LatePaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BillPaymentService {

    private final ObjectMapper objectMapper;

    //Send noti to email
    public void latePaymentNotification(String payload) throws JsonProcessingException {
        List<LatePaymentResponse> request = objectMapper.readValue(payload, new TypeReference<List<LatePaymentResponse>>(){});
        DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        log.info(String.format("notification date : %s -> information : %s", LocalDateTime.now().format(datetimeFormat),objectMapper.writeValueAsString(request)));
    }
}
