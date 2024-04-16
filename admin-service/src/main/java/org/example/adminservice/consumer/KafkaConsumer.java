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
    private static final String ADMIN_REQUEST_TOPIC = "admin-request-topic";
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);

    private final ConsumerService consumerService;

    public KafkaConsumer(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }


    @KafkaListener(topics = ADMIN_REQUEST_TOPIC, groupId = "admin-service")
    public void listen(ConsumerRecord<String, AdminRequest> consumerRecord, Acknowledgment ak) {

        try {
            // Log message receipt
            LOG.info("** Admin request received: key: {}",consumerRecord.key());
            LOG.info("** value: {}",consumerRecord.value());
            //made modifications in bd
            consumerService.updateNumberOfSeatsInDatabase(consumerRecord.value());
            // Acknowledge the message
            ak.acknowledge();

        } catch (Exception e) {
            LOG.error("Error processing admin request: {}", e.getMessage(), e);
            // Acknowledge message in case of error
            ak.acknowledge();
        }

    }

}

