package org.example.paymentservice.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.example.dto.PaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


public class KafkaProducerConfig {

    // Injecting properties from application.properties
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private String deliveryTimeout;

    @Value("${spring.kafka.producer.properties.linger.ms}")
    private String linger;

    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private String requestTimeout;

    // Configuration for Kafka producer
    Map<String, Object> producerConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
        config.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
        return config;
    }

    // Bean for producing Kafka messages
    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    // Kafka template for booking topic
    @Primary
    @Bean
    KafkaTemplate<String, Object> bookingKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Kafka template for notification topic
    @Bean
    KafkaTemplate<String, Object> notificationKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Bean for creating the payment response topic
    @Bean
    NewTopic createTopicBooking() {
        return TopicBuilder.name("payment-response-topic").partitions(3).replicas(2)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    // Bean for creating the notification topic
    @Bean
    NewTopic createTopicNotification() {
        return TopicBuilder.name("notification-topic").partitions(3).replicas(2)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

}