package org.example.adminservice.consumer.dlt;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DltConsumerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DltConsumerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToDLT(ConsumerRecord<?,?> consumerRecord){
        kafkaTemplate.send("admin-request-topic.DLT",consumerRecord.key().toString(), consumerRecord.value());
    }
}
