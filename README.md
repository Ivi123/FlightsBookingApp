# FlightsBookingApp
- **Admin**:
  ***Manage Airline Operators***
    - Register airline operators (e.g., Lufthansa, TAROM, etc.) with their IBAN for payment.
    - Register API for each operator (each operator will define an API for a specific function; for example, for flight search 'http://lufthansa.com/searchFlight').
    - Connect with operator user and perform operations: register destinations (e.g., Bucharest - Timisoara flight operated by TAROM) and the number of available seats on the platform.
    - Read Kafka topic and update the number of available seats with the value from the received message.
    - Send a message back to the booking service with the status (ok/nok) for each update request.

- **Flight-search** [Webflux + Gateway pattern for result aggregation]:
  ***Search for flights between all operators registered on the platform based on specific criteria.***
    - Criteria for searching flights with available seats:
        - Date range + start destination
        - Date range + start destination + end destination
        - Start destination + end destination
    - Optional: Add various filters.
    - Optional: Sorting and pagination.
      Based on these criteria, search for operators from the list of registered operators who have available flights on the desired route. For each found operator, call the corresponding API, and the results will be aggregated and presented.

- **Booking** [Webflux + Kafka + DB]:
    - Receive booking details and save them in the database (with an expiration term) with the status RESERVED.
    - Send a message to the admin with an update of the number of available seats (how many are in reservation).
    - Simultaneously, send a message on a topic where the payment service listens with all payment details (booking details + IBAN + amount + IBAN registered at the operator (the account that receives the money)).
    - Also, send a message to the Notification topic.
    - If the expiration term has passed, change the status to rejected and send an update on Kafka to the admin service (updating the number of available seats + how many are in reservation).
    - Listen to messages from the payment service and update the corresponding status.
      If payment is not successful (nok), send an update on Kafka to the admin service (updating the number of available seats + how many are in reservation).
    - Listen to messages from the admin and send a notification email to the client with the reservation status.

- **Payment** [Webflux + Kafka + DB]:
  ***Payment service listens on a topic and initiates a payment (saves the received data in the database with the status INITIATED).***
    - The payment service calls an external mock REST API of a payment operator (e.g., Netopia) and sends a notification.
    - After completing the payment (SUCCESS / REJECTED), send a message on a topic that the booking service listens to + a message on the Notification topic.

- **Notifications**:
  ***Listen on a topic and save messages in ELASTICSEARCH.***
    - Provide an endpoint that is called from the UI every 30 seconds to check if the user has new notifications.

- **Check-in** - to be defined.