package org.example.paymentservice.consumer.dlt;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.dto.PaymentRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DltConsumerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DltConsumerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToDLT(ConsumerRecord<?,?> consumerRecord){
        kafkaTemplate.send("payment-request-topic.DLT",consumerRecord.key().toString(), consumerRecord.value());
    }
}
