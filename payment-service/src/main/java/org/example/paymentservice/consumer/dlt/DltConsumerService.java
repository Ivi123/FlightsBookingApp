package org.example.paymentservice.consumer.dlt;

import avro.PaymentRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DltConsumerService {
    private static final String PAYMENT_REQUEST_TOPIC_DLT = "payment-request-topic.DLT";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DltConsumerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToDLT(ConsumerRecord<?, ?> consumerRecord) {
        kafkaTemplate.send(PAYMENT_REQUEST_TOPIC_DLT, consumerRecord.key().toString(),
                consumerRecord.value().toString());
    }

    public void sendPaymentToDLT(PaymentRequest paymentRequest) {
        kafkaTemplate.send(PAYMENT_REQUEST_TOPIC_DLT, paymentRequest.getId().toString(),
                paymentRequest);
    }
}
