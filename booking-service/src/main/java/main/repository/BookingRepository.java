package main.repository;

import main.model.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
    Mono<Booking> findByPaymentDetails_CardNumberAndFlightDetails_FlightId(String cardNumber, String flightId);

}
