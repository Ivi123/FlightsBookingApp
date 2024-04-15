package main.kafka.consumer;
import avro.AdminRequest;
import avro.BookingNotification;
import avro.PaymentRequest;
import main.constants.notifications.NotificationMessagesConstants;
import main.kafka.mappers.BookingNotificationMapper;
import main.kafka.producer.BookingProducerService;
import main.mapper.BookingMapper;
import main.kafka.mappers.AdminRequestMapper;
import main.kafka.mappers.PaymentRequestMapper;
import main.model.Booking;
import main.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    private BookingNotificationMapper bookingNotificationMapper;

    @Autowired
    private BookingProducerService bookingProducerService;

    @KafkaListener(topics = "admin-response-topic", groupId = "admin-response-group", containerFactory = "adminRequestKafkaListenerContainerFactory")
    public void handleAdminResponse(AdminRequest adminResponse) {
        Mono<Booking> bookingMono = bookingRepository.findById(adminResponse.getBookingId());
        bookingMono.subscribe(System.out::println);

        bookingMono.doOnSubscribe(sub -> System.out.println("Subscribed to fetch booking with ID: " + adminResponse.getBookingId()))
                .flatMap(booking -> {
                    if (adminResponse.getStatus().equalsIgnoreCase("SUCCEEDED")) {
                        booking.setStatus(adminResponse.getStatus());
                        // Send successful booking notification
                        BookingNotification bookingSuccessfulNotification = bookingNotificationMapper
                                .toBookingNotification(booking, NotificationMessagesConstants.BOOKING_SUCCESSFUL_MESSAGE);
                        bookingProducerService
                                .sendBookingNotificationRequest(booking.getId(), bookingSuccessfulNotification);
                        // Check if booking hasn't expired
                        if (booking.getExpiresAt().isAfter(LocalDateTime.now())) {
                            // Prepare and send payment request
                            PaymentRequest paymentRequest = paymentRequestMapper
                                    .toPaymentRequest(booking);
                            paymentRequest.setStatus("INITIATED");
                            bookingProducerService
                                    .sendPaymentRequest(booking.getId(), paymentRequest);
                        } else {
                            // Handle expired booking
                            booking.setStatus("EXPIRED");
                            AdminRequest adminRequest = adminRequestMapper.toAdminRequest(booking);
                            bookingProducerService
                                    .sendAdminRequest(booking.getId(), adminRequest);

                            // Send expired booking notification
                            BookingNotification bookingExpiredNotification = bookingNotificationMapper
                                    .toBookingNotification(booking, NotificationMessagesConstants.BOOKING_EXPIRED_MESSAGE);

                            bookingProducerService
                                    .sendBookingNotificationRequest(booking.getId(), bookingExpiredNotification);

                            return bookingRepository.save(booking).doOnSuccess(b -> System.out.println("Booking saved as EXPIRED"));

                        }
                    } else {
                        // Handle admin rejection
                        // Send rejected booking notification
                        booking.setStatus(adminResponse.getStatus());
                        BookingNotification bookingRejectedNotification = bookingNotificationMapper
                                .toBookingNotification(booking, NotificationMessagesConstants.BOOKING_REJECTED_MESSAGE);
                        bookingProducerService
                                .sendBookingNotificationRequest(booking.getId(), bookingRejectedNotification);
                        // Save booking as FAILED in the database
                        return bookingRepository.save(booking).doOnSuccess(b -> System.out.println("Booking saved as FAILED"));
                    }
                    return Mono.empty();
                }).switchIfEmpty(
                        Mono.fromRunnable(() -> System.out.println("Booking not found for ID: " + adminResponse.getBookingId()))
                ).subscribe();
    }
}

