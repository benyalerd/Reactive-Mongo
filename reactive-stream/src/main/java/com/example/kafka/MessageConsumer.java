package com.example.kafka;

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

    @RetryableTopic(kafkaTemplate = "kafkaTemplate",
            concurrency = "1",
            sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC,
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )

    @KafkaListener(topics = "dev-demo", groupId = "my-group-id")
    public void listen(String message) throws Exception {
        log.info("Received message: " + message);
        handleEvent(message);
    }

    private void handleEvent(String message) throws Exception {
        switch (message) {
            case "1":
                log.info("no process found on: " + message);
                return;
            default:
                log.info("no process found on: " + message);
        }
    }
}
