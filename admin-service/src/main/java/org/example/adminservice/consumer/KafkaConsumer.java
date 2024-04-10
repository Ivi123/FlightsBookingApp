package org.example.adminservice.consumer;

import avro.AdminRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class KafkaConsumer {
    private static final String ADMIN_REQUEST_TOPIC ="admin-request-topic";
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);


    @KafkaListener(topics = ADMIN_REQUEST_TOPIC, groupId = "admin-service")
    public void listen(ConsumerRecord<String, AdminRequest> consumerRecord, Acknowledgment ak) {

        try {
            AdminRequest adminRequest = consumerRecord.value();

            // Log message receipt
            log.info(MessageFormat.format("Admin request received: value: {0} key: {1}", consumerRecord.value(), consumerRecord.key()));
                        // Acknowledge the message
                        ak.acknowledge();

        } catch (Exception e) {
            log.error("Error processing admin request: {}", e.getMessage(), e);
            // Acknowledge message in case of error
            ak.acknowledge();
        }

    }

}

