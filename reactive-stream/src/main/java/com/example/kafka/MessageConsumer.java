package com.example.kafka;

import com.example.kafka.dto.Outbox;
import com.example.service.BillPaymentStreamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@AllArgsConstructor
public class MessageConsumer {

    private final ObjectMapper objectMapper;
    private final BillPaymentStreamService billPaymentService;
    @RetryableTopic(kafkaTemplate = "kafkaTemplate",
            concurrency = "1",
            sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC,
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )

    @KafkaListener(topics = "dev-demo", groupId = "my-group-id")
    public void listen(String message) throws Exception {
        log.info("Received message: " + message);
        Outbox outbox = objectMapper.readValue(message, Outbox.class);
        handleEvent(outbox);
    }

    private void handleEvent(Outbox message) throws Exception {
        switch (EventType.fromValue(message.getType())) {
            case LATE_PAYMENT_NOTIFICATION:
                billPaymentService.latePaymentNotification(message.getPayload());
                return;
            default:
        }
    }
}
