package main.kafka.producer;


import avro.AdminRequest;
import avro.PaymentRequest;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingProducerService {

    private final KafkaTemplate<String, SpecificRecord> avroKafkaTemplate;

    @Autowired
    public BookingProducerService(KafkaTemplate<String, SpecificRecord> avroKafkaTemplate) {
        this.avroKafkaTemplate = avroKafkaTemplate;
    }

    public void sendAdminRequest(String topic, AdminRequest adminRequest) {
        avroKafkaTemplate.send(topic, adminRequest.getBookingId(), adminRequest);
    }

    public void sendPaymentRequest(String topic, PaymentRequest paymentRequest) {
        avroKafkaTemplate.send(topic, paymentRequest.getBookingId(), paymentRequest);
    }
}

