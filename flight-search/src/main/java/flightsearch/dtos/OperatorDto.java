package flightsearch.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OperatorDto {
    private String id;
    @NotNull(message = "Code cannot be null")
    @NotBlank(message = "Code cannot be blank")
    private String code;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "IBAN cannot be null")
    @NotBlank(message = "IBAN cannot be blank")
    private String IBAN;

    public OperatorDto() {
    }

    public OperatorDto(String id, String code, String name, String IBAN) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.IBAN = IBAN;
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

    public void setId(String id) {
        this.id = id;
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
