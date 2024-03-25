package org.example.adminservice.functionality.dto;

import org.example.adminservice.functionality.model.FunctionalityType;

public class UpdateFunctionalityDto {
    private String URL;
    private FunctionalityType type;

    public UpdateFunctionalityDto(String URL, FunctionalityType functionalityType) {
        this.URL = URL;
        this.type = functionalityType;
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

    public void setType(FunctionalityType functionalityType) {
        this.type = functionalityType;
    }
}
