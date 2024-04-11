package main.service;

import main.dto.BookingDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface BookingService {
    Mono<BookingDTO> createBooking(Mono<BookingDTO> bookingDTO);

    Mono<BookingDTO> getBookingById(String id);

    Flux<BookingDTO> getAllBookings();

    Mono<BookingDTO> updateBooking(String id, Mono<BookingDTO> bookingDTO);

    Mono<Void> deleteBooking(String id);
}
