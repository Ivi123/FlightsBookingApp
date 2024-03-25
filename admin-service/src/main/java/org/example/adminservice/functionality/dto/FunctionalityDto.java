package org.example.adminservice.functionality.dto;

import jakarta.validation.constraints.NotNull;
import org.example.adminservice.functionality.model.FunctionalityType;

public class FunctionalityDto {
    @NotNull(message = "Operator Id cannot be null")
    private String operatorId;
    @NotNull(message = "URL cannot be null")
    private String URL;
    @NotNull(message = "Functionality type cannot be null")
    private FunctionalityType type;

    public FunctionalityDto(String operatorId,
                            String URL,
                            FunctionalityType type) {
        this.operatorId = operatorId;
        this.URL = URL;
        this.type = type;
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
