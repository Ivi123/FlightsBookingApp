package org.example.paymentservice.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.dto.PaymentRequest;
import org.example.paymentservice.mapper.PaymentMapper;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.service.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class KafkaConsumer {
    private final PaymentServiceImpl paymentService;
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    // Constructor injection of PaymentService
    public KafkaConsumer(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    // Kafka message listener method
    @KafkaListener(topics = "payment-request-topic", groupId = "payment-service")
    public void listen(ConsumerRecord<String, PaymentRequest> consumerRecord, Acknowledgment ak) {
        try {
            PaymentRequest paymentRequest = consumerRecord.value();

            // Log message receipt
            log.info(MessageFormat.format("Payment request received: value: {0} key: {1}", consumerRecord.value(), consumerRecord.key()));

            // Save payment details to database
            Payment payment = PaymentMapper.paymentRequestToPayment(paymentRequest);

            // Check if the message has already been processed
            Payment existingPayment = paymentService.findByCustomIdAndBookingId(payment.getId(), payment.getBookingId());
            if (existingPayment != null) {
                log.error("Found a duplicate message: paymentId: {}", consumerRecord.key());
                return;
            }
            paymentService.savePayment(payment);
            log.info("Payment: {} saved in database!", payment);

            // Display the online payment link
            log.info("Please make the payment here: ");
            String email =payment.getCardHolderName().trim()+"@gmail.com";
            String paymentLink = "http://localhost:8085/?" +
                    "paymentId=" + payment.getId() +
                    "&bookingId=" + payment.getBookingId() +
                    "&amount=" + payment.getPrice() +
                    "&email=" + email;
            log.info(paymentLink);
            // Acknowledge the message
            ak.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
