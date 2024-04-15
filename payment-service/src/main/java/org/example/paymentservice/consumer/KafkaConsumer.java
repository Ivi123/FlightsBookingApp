package org.example.paymentservice.consumer;

import avro.PaymentRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.paymentservice.consumer.dlt.DltConsumerService;
import org.example.paymentservice.mapper.PaymentMapper;
import org.example.paymentservice.model.Payment;
import org.example.paymentservice.service.stripe.StripeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
public class KafkaConsumer {
    private static final String PAYMENT_REQUEST_TOPIC = "payment-request-topic";
    private final StripeServiceImpl paymentService;
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final DltConsumerService dltConsumerService;

    // Constructor injection of PaymentService
    public KafkaConsumer(StripeServiceImpl paymentService, DltConsumerService dltConsumerService) {
        this.paymentService = paymentService;
        this.dltConsumerService = dltConsumerService;
    }

    @KafkaListener(topics = PAYMENT_REQUEST_TOPIC, groupId = "payment-service")
    public void listen(ConsumerRecord<String, PaymentRequest> consumerRecord, Acknowledgment ak) {
        try {
            PaymentRequest paymentRequest = consumerRecord.value();

            // Log message receipt
            log.info(MessageFormat.format("Payment request received: value: {0} key: {1}", consumerRecord.value(), consumerRecord.key()));

            // Map payment request to Payment entity
            Payment payment = PaymentMapper.paymentRequestToPayment(paymentRequest);

            //set creation time and expiration time for payment
            payment.setCreationTime(LocalDateTime.now());
            payment.setExpirationTime(LocalDateTime.now().plusMinutes(15));
            String email = payment.getCardHolderName().split(" ")[0]+
                    payment.getCardHolderName().split(" ")[1] + "@gmail.com";
            payment.setUserEmail(email);

            // Save payment details to database
            paymentService.savePayment(payment)
                    .doOnSuccess(savedPayment -> {
                        // Display the online payment link
                        log.info("Payment saved in database : {}", savedPayment);

                        String paymentLink = "http://localhost:8085/?" +
                                "paymentId=" + savedPayment.getId() +
                                "&bookingId=" + savedPayment.getBookingId() +
                                "&amount=" + savedPayment.getPrice() +
                                "&email=" + email;
                        log.info("Please make the payment here: {}", paymentLink);
                        // Acknowledge the message
                        ak.acknowledge();
                    })
                    .doOnError(error -> {
                        log.error("Failed to save payment: {}", error.getMessage(), error);
                        //move to dlt
                        dltConsumerService.sendToDLT(consumerRecord);
                        // Acknowledge message in case of error
                        ak.acknowledge();
                    })
                    .subscribe(); // Subscribe to trigger the execution of the reactive chain
        } catch (Exception e) {
            log.error("Error processing payment request: {}", e.getMessage(), e);
            // Acknowledge message in case of error
            ak.acknowledge();
        }
    }

}
