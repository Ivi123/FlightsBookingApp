package org.example.adminservice.producer;

import avro.AdminRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {
    private static final String ADMIN_RESPONSE_TOPIC ="admin-response-topic";
    private KafkaTemplate<String, Object> template;

    public KafkaProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void send(String key, AdminRequest request){
        template.send(ADMIN_RESPONSE_TOPIC,request);
        CompletableFuture<SendResult<String, Object>> future = template.send(ADMIN_RESPONSE_TOPIC,key, request);
        future.whenComplete((result,ex)-> {
            if (ex == null) {
                System.out.println("Sent message = " + request + " with offset= " + result.getRecordMetadata().offset());
            } else {
                System.out.println("Unable to send message : "+ ex.getMessage());

            }

        });

    }
}
