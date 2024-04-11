package org.example.paymentservice.config.kafka;

import avro.PaymentRequest;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    Environment environment;

    // Constructor injection for Environment
    public KafkaConsumerConfig(Environment environment) {
        this.environment = environment;
    }

    // Define Consumer Factory Bean
    @Bean
    public ConsumerFactory<String, PaymentRequest> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.consumer.bootstrap-servers"));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, environment.getProperty("spring.kafka.consumer.properties.spring.json.trusted.packages"));
        config.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));
        config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        // Disable auto commit
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // Set the schema registry URL
        config.put("schema.registry.url", "http://localhost:8081");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    // Define Kafka Listener Container Factory Bean
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, PaymentRequest> kafkaListenerContainerFactory(
            ConsumerFactory<String, PaymentRequest> consumerFactory, CommonErrorHandler commonErrorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, PaymentRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // Set acknowledgment mode to MANUAL_IMMEDIATE
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(commonErrorHandler);
        return factory;
    }

}
