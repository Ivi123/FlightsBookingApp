package org.example.adminservice.config;

import org.example.adminservice.flight.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FlightDataInitializer implements ApplicationRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] operators = {"6628adddbec58103b3a1b699", "6628ae33bec58103b3a1b69a"};
        String departure = "New York";
        String destination = "London";

        long count = mongoTemplate.count(new Query(), Flight.class);

        if (count == 0) {
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
            LocalDate currentDate = startDate;

            while (!currentDate.isAfter(endDate)) {
                for (String operatorId : operators) {
                    Flight flight = new Flight();
                    flight.setOperatorId(operatorId);
                    flight.setDeparture(departure);
                    flight.setDestination(destination);
                    flight.setNumberOfSeats(100);
                    flight.setDate(currentDate.format(DateTimeFormatter.ISO_DATE));

                    mongoTemplate.save(flight);
                }

                currentDate = currentDate.plusDays(1);
            }
        }
    }
}
