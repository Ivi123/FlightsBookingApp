package org.example.adminservice.operator.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Operator {
    @Id
    private String id;
    private String name;
    private String IBAN;

    public Operator(String name, String IBAN) {
        this.name = name;
        this.IBAN = IBAN;
    }
}
