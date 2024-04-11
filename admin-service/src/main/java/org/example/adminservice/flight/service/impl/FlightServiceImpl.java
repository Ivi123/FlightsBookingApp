package org.example.adminservice.flight.service.impl;

import org.example.adminservice.flight.dto.FlightDto;
import org.example.adminservice.flight.exception.FlightNotFoundException;
import org.example.adminservice.flight.mapper.FlightMapper;
import org.example.adminservice.flight.model.Flight;
import org.example.adminservice.flight.repository.FlightRepository;
import org.example.adminservice.flight.service.FlightService;
import org.example.adminservice.operator.exception.OperatorNotFoundException;
import org.example.adminservice.operator.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private FlightMapper flightMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<FlightDto> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDto getFlightById(String id) {
        return flightRepository.findById(id)
                .map(flightMapper::entityToDto)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    @Override
    public FlightDto createFlight(FlightDto flightDTO) {
        Flight flight = flightMapper.dtoToEntity(flightDTO);
        if (operatorRepository.existsById(flight.getOperatorId())) {
            return flightMapper.entityToDto(flightRepository.save(flight));
        } else {
            throw new OperatorNotFoundException(flight.getOperatorId());
        }
    }

    @Override
    public FlightDto updateFlight(FlightDto flightDTO) {
        if (operatorRepository.existsById(flightDTO.getOperatorId())) {
            return flightRepository.findById(flightDTO.getId())
                    .map(existingFlight -> {
                        existingFlight.setDeparture(flightDTO.getDeparture());
                        existingFlight.setDestination(flightDTO.getDestination());
                        existingFlight.setOperatorId(flightDTO.getOperatorId());
                        existingFlight.setNumberOfSeats(flightDTO.getNumberOfSeats());
                        return flightMapper.entityToDto(flightRepository.save(existingFlight));
                    })
                    .orElseThrow(() -> new FlightNotFoundException(flightDTO.getId()));
        } else {
            throw new OperatorNotFoundException(flightDTO.getOperatorId());
        }
    }

    @Override
    public void deleteFlight(String id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        } else {
            throw new FlightNotFoundException(id);
        }
    }

    @Override
    public List<Flight> findByDepartureDestinationAndDate(String departure, String destination, String dateFrom, String dateTo) {
        Query query = new Query();
        if (!departure.isEmpty()) {
            query.addCriteria(Criteria.where("departure").is(departure));
        }
        if (!destination.isEmpty()) {
            query.addCriteria(Criteria.where("destination").is(destination));
        }
        if (!dateFrom.isEmpty() && !dateTo.isEmpty()) {
            query.addCriteria(Criteria.where("date").gte(dateFrom).lte(dateTo));
        }
        query.addCriteria(Criteria.where("numberOfSeats").gt(0));

        return mongoTemplate.find(query, Flight.class);
    }

    @Override
    public Flight findByDepartureDestinationDateAndOperatorId(String departure, String destination, String date, String operatorId) {
        Query query = new Query();
        if (!departure.isEmpty()) {
            query.addCriteria(Criteria.where("departure").is(departure));
        }
        if (!destination.isEmpty()) {
            query.addCriteria(Criteria.where("destination").is(destination));
        }
        if (!operatorId.isEmpty()) {
            query.addCriteria(Criteria.where("operatorId").is(operatorId));
        }
        return mongoTemplate.findOne(query, Flight.class);
    }



}

