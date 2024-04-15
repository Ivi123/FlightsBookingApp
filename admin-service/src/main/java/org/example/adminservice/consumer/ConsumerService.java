package org.example.adminservice.consumer;

import avro.AdminRequest;
import org.apache.juli.logging.Log;
import org.example.adminservice.flight.dto.FlightDto;
import org.example.adminservice.flight.mapper.FlightMapper;
import org.example.adminservice.flight.model.Flight;
import org.example.adminservice.flight.service.FlightService;
import org.example.adminservice.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private final KafkaProducer producer;
    private final FlightMapper flightMapper;
    private final FlightService flightService;
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);

    public ConsumerService(KafkaProducer producer, FlightMapper flightMapper, FlightService flightService) {
        this.producer = producer;
        this.flightMapper = flightMapper;
        this.flightService = flightService;
    }

    public void updateNumberOfSeatsInDatabase(AdminRequest adminRequest) {
        Flight flight = flightService.findByDepartureDestinationDateAndOperatorId(adminRequest.getDeparture(),
                adminRequest.getDestination(), adminRequest.getDate(),
                adminRequest.getOperatorId());
        if (!(adminRequest.getStatus().equalsIgnoreCase("EXPIRED") ||
                adminRequest.getStatus().equalsIgnoreCase("FAILED"))) {
            if (flight.getNumberOfSeats() < adminRequest.getNumberOfSeats()) {
                adminRequest.setStatus("FAILED");
                //send to topic
                producer.send(adminRequest.getBookingId(), adminRequest);
                return;
            }
            flight.setNumberOfSeats(flight.getNumberOfSeats() - adminRequest.getNumberOfSeats());
            adminRequest.setStatus("SUCCEEDED");
        } else {
            flight.setNumberOfSeats(flight.getNumberOfSeats() + adminRequest.getNumberOfSeats());
            FlightDto dto = flightMapper.entityToDto(flight);
            LOG.info("Number of seats: {}", flight.getNumberOfSeats());
            flightService.updateFlight(dto);
            return;
        }
        //update number of seats in bd
        FlightDto dto = flightMapper.entityToDto(flight);
        LOG.info("Number of seats: {}", flight.getNumberOfSeats());
        flightService.updateFlight(dto);
        //send message to kafka
        producer.send(adminRequest.getBookingId(), adminRequest);

    }
}
