Feature: Flight Search Integration

  Scenario: User searches for flights
    Given the admin service has flights available for departure "Bucharest" and destination "London" on "2024-06-15"
    And the external service for operator "lufthansa" is available
    And the external service for operator "tarom" is available
    When the user searches for flights from "Bucharest" to "London" between "2024-05-20" and "2024-09-10"
    Then the user receives a list of available flights from both "Lufthansa" and "Tarom"

