package com.example.service;

import com.example.core.dto.response.LatePaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class BillPaymentStreamService {

    private final ObjectMapper objectMapper;

    private final EmailService mailService;

    //Send noti to email
    public void latePaymentNotification(String payload) throws JsonProcessingException {
        List<LatePaymentResponse> result = objectMapper.readValue(payload, new TypeReference<List<LatePaymentResponse>>() {
        });
        DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        log.info(String.format("notification date : %s -> information : %s", LocalDateTime.now().format(datetimeFormat), objectMapper.writeValueAsString(result)));
        //Send email

        result.forEach(x -> {
            String mailTo = x.getEmail();

            Map<String, String> model = new HashMap<>();
            model.put("fullname", x.getFirstname() + " " + x.getLastname());
            model.put("totalAmount", x.getAmount().toString() + " THB");
            mailService.sendLatePaymentNotification(mailTo, model);
        });
    }
}
