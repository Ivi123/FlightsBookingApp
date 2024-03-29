package org.example.adminservice.operator.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Operator {
    @Id
    private String id;
    private String code;
    private String name;
    private String IBAN;

    public Operator(String code, String name, String IBAN) {
        this.code = code;
        this.name = name;
        this.IBAN = IBAN;
    }
    public Operator(){

    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }
}
