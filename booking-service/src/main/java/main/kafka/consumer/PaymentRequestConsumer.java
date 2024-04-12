package main.kafka.consumer;

import avro.AdminRequest;
import avro.BookingNotification;
import avro.PaymentRequest;
import main.constants.notifications.NotificationMessagesConstants;
import main.kafka.producer.BookingProducerService;
import main.mapper.PaymentDetailsMapper;
import main.kafka.mappers.AdminRequestMapper;
import main.kafka.mappers.NotificationRequestMapper;
import main.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class PaymentRequestConsumer {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentDetailsMapper paymentDetailsMapper;
    @Autowired
    private AdminRequestMapper adminRequestMapper;
    @Autowired
    private NotificationRequestMapper notificationRequestMapper;
    @Autowired
    private BookingProducerService bookingProducerService;

    @KafkaListener(topics = "payment-response-topic", groupId = "payment-response-group", containerFactory = "paymentRequestKafkaListenerContainerFactory")
    public void handlePaymentDetailsResponse(PaymentRequest paymentResponse) {
        bookingRepository.findById(paymentResponse.getBookingId()).flatMap(booking -> {
            if (paymentResponse.getStatus().equals("SUCCEEDED")) {
                // Update booking status and notify
                booking.setStatus("COMPLETED");

                // Send payment successful notification
                BookingNotification paymentSuccessfulNotification = notificationRequestMapper
                        .toBookingNotification(booking, NotificationMessagesConstants.PAYMEMT_SUCCESSFUL_MESSAGE);
                bookingProducerService
                        .sendPaymentNotificationRequest(booking.getId(), paymentSuccessfulNotification);
            } else {
                // Update booking status, notify, and inform admin to revert seats
                booking.setStatus("FAILED");

                // Send payment failed notification
                BookingNotification paymentFailedNotification = notificationRequestMapper
                        .toBookingNotification(booking, NotificationMessagesConstants.PAYMENT_UNSUCCESSFUL_MESSAGE);
                bookingProducerService
                        .sendPaymentNotificationRequest(booking.getId(), paymentFailedNotification);

                // Send message to admin to revert the request
                AdminRequest revertRequest = adminRequestMapper.toAdminRequest(booking);
                revertRequest.setStatus("FAILED");
                bookingProducerService.sendAdminRequest(booking.getId(), revertRequest);
            }
            return bookingRepository.save(booking);
        }).subscribe();
    }
}
