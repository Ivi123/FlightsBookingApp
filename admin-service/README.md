- **Admin**:
  ***Manage Airline Operators***
    - Register airline operators (e.g., Lufthansa, TAROM, etc.) with their IBAN for payment.
    - Register API for each operator (each operator will define an API for a specific function; for example, for flight
      search 'http://lufthansa.com/searchFlight').
    - Connect with operator user and perform operations: register destinations (e.g., Bucharest - Timisoara flight
      operated by TAROM) and the number of available seats on the platform.
    - Read Kafka topic and update the number of available seats with the value from the received message.
    - Send a message back to the booking service with the status (ok/nok) for each update request.

### Domain model:

![Alt text](assets/adminDiagram.png)

### API's for admin-service

#### Examples of request/response:

###### For add operator:

#### POST :http://localhost:8080/operator/add

```
{
    "code":"ROT",
    "name":"TAROM",
    "iban":"RO53RNCB0082005630560001"
}
```

Response:

````
{
    "id": "66014ba0795e9d6bda686019",
    "code": "ROT",
    "name": "TAROM",
    "iban": "RO53RNCB0082005630560001"
}
````
#### For GET all Operators: GET http://localhost:8080/operator/all

Response:

```
[
    {
        "id": "66014ba0795e9d6bda686019",
        "code": "ROT",
        "name": "TAROM",
        "iban": "RO53RNCB0082005630560001"
    },
    {
        "id": "66014e15795e9d6bda68601a",
        "code": "WZZ",
        "name": "Wizz Air",
        "iban": "RO45BTRLRONCRT0425283901"
    },
    {
        "id": "66014ea5795e9d6bda68601b",
        "code": "THY",
        "name": "Turkish Airlines",
        "iban": " RO19BACX0000001124019000"
    }
]
```
#### For UPDATE Iban operator : Put http://localhost:8080/operator/modify-iban/id
```
{
   "iban":" RO19BACX0000001124019001"
}
```
Response:
```
{
    "id": "66014ba0795e9d6bda686019",
    "code": "ROT",
    "name": "TAROM",
    "iban": " RO19BACX0000001124019001"
}
```