package main.controller;

import main.dto.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import main.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/add")
    public Mono<ResponseEntity<BookingDTO>> createBooking(@RequestBody Mono<BookingDTO> bookingDTO, @AuthenticationPrincipal Jwt jwt) {
        return bookingService.createBooking(bookingDTO, jwt)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<BookingDTO>> getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public Flux<ResponseEntity<BookingDTO>> getAllBookings() {
        return bookingService.getAllBookings()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<BookingDTO>> updateBooking(@PathVariable String id, @RequestBody Mono<BookingDTO> bookingDTO) {
        return bookingService.updateBooking(id, bookingDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

        //TODO: send kafka topic example, to be replaced by real functionality for update booking

    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBooking(@PathVariable String id) {
        return bookingService.deleteBooking(id);
        //TODO: send kafka topic example, to be replaced by real functionality for cancel booking

    }
}


