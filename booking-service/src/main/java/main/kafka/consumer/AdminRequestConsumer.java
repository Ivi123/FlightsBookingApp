package main.kafka.consumer;
import avro.AdminRequest;
import avro.BookingNotification;
import avro.PaymentRequest;
import main.constants.notifications.NotificationMessagesConstants;
import main.kafka.mappers.NotificationRequestMapper;
import main.kafka.producer.BookingProducerService;
import main.mapper.BookingMapper;
import main.kafka.mappers.AdminRequestMapper;
import main.kafka.mappers.PaymentRequestMapper;
import main.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminRequestConsumer {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRequestMapper paymentRequestMapper;

    @Autowired
    private AdminRequestMapper adminRequestMapper;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private NotificationRequestMapper notificationRequestMapper;

    @Autowired
    private BookingProducerService bookingProducerService;

    @KafkaListener(topics = "admin-response-topic", groupId = "admin-response-group", containerFactory = "adminRequestKafkaListenerContainerFactory")
    public void handleAdminResponse(AdminRequest adminResponse) {
        bookingRepository.findById(adminResponse.getBookingId()).flatMap(booking -> {
            if (adminResponse.getStatus().equals("SUCCEEDED")) {
                // Send successful booking notification
                BookingNotification bookingSuccessfulNotification = notificationRequestMapper
                        .toBookingNotification(booking, NotificationMessagesConstants.BOOKING_SUCCESSFUL_MESSAGE);
                bookingProducerService
                        .sendBookingNotificationRequest(booking.getBookingId(), bookingSuccessfulNotification);
                // Check if booking hasn't expired
                if (booking.getExpiresAt().isAfter(LocalDateTime.now())) {
                    // Prepare and send payment request
                    PaymentRequest paymentRequest = paymentRequestMapper
                            .toPaymentRequest(bookingMapper.toDTO(booking));
                    bookingProducerService
                            .sendPaymentRequest(booking.getBookingId(), paymentRequest);
                } else {
                    // Handle expired booking
                    booking.setStatus("EXPIRED");
                    AdminRequest adminRequest = adminRequestMapper.toAdminRequest(booking);
                    bookingProducerService
                            .sendAdminRequest(booking.getBookingId(), adminRequest);

                    // Send expired booking notification
                    BookingNotification bookingExpiredNotification = notificationRequestMapper.toBookingNotification(booking, NotificationMessagesConstants.BOOKING_EXPIRED_MESSAGE);
                    bookingProducerService
                            .sendBookingNotificationRequest(booking.getBookingId(), bookingExpiredNotification);

                    return bookingRepository.save(booking);

                }
            } else {
                // Handle admin rejection
                // Send rejected booking notification
                BookingNotification bookingRejectedNotification = notificationRequestMapper
                        .toBookingNotification(booking, NotificationMessagesConstants.BOOKING_REJECTED_MESSAGE);
                bookingProducerService
                        .sendBookingNotificationRequest(booking.getBookingId(), bookingRejectedNotification);
                // Save booking as FAILED in the database
                return bookingRepository.save(booking);
            }
            return null;
        }).subscribe();
    }
}

