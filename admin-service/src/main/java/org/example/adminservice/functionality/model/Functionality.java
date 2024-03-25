package org.example.adminservice.functionality.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Functionality {
    @Id
    private String id;
    private String operatorId;
    private String URL;
    private FunctionalityType type;

    public Functionality() {
    }

    public Functionality(String id,
                         String operatorId,
                         String URL,
                         FunctionalityType type) {
        this.id = id;
        this.operatorId = operatorId;
        this.URL = URL;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public FunctionalityType getType() {
        return type;
    }

    public void setType(FunctionalityType type) {
        this.type = type;
    }
}
