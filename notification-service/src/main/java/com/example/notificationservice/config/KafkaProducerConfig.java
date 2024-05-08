package com.example.notificationservice.config;

import avro.BookingNotification;
import avro.PaymentNotification;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Bean
    public ProducerFactory<String, PaymentNotification> producerFactoryForPayment() {
        Map<String, Object> configProps = getStringObjectMap();

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, BookingNotification> producerFactoryForBooking() {
        Map<String, Object> configProps = getStringObjectMap();

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, PaymentNotification> kafkaPaymentTemplate() {
        return new KafkaTemplate<>(producerFactoryForPayment());
    }

    @Bean
    public KafkaTemplate<String, BookingNotification> kafkaBookingTemplate() {
        return new KafkaTemplate<>(producerFactoryForBooking());
    }

    private Map<String, Object> getStringObjectMap() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        configProps.put("schema.registry.url", "http://localhost:8081");

        return configProps;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactoryForDLT() {
        Map<String, Object> configProps = getStringObjectMap();

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaDLTTemplate() {
        return new KafkaTemplate<>(producerFactoryForDLT());
    }

    @Bean
    public KafkaListenerErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaDLTTemplate) {
        return (message, exception) -> {// Extrage ConsumerRecord din payload
            ConsumerRecord<?, ?> consumerRecord = (ConsumerRecord<?, ?>) message.getPayload();

            // Obțin numele topicului din ConsumerRecord
            String topic = consumerRecord.topic();

            // Construiesc numele topicului DLT
            String dltTopic = topic + "-dlt";

            // Trimit mesajul la DLT
            kafkaDLTTemplate.send(dltTopic, message.getPayload().toString());
            return null;
        };
    }
}
