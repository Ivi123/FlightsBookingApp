package main.service;

import main.dto.BookingDTO;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface BookingService {
    Mono<BookingDTO> createBooking(Mono<BookingDTO> bookingDTO, Jwt jwt);

    Mono<BookingDTO> getBookingById(String id);

    Flux<BookingDTO> getAllBookings();

    Mono<BookingDTO> updateBooking(String id, Mono<BookingDTO> bookingDTO);

    Mono<Void> deleteBooking(String id);
}
