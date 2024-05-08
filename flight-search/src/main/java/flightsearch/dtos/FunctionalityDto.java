package flightsearch.dtos;

public class FunctionalityDto {
    private String id;
    private String operatorId;
    private String URL;
    private FunctionalityType type;

    public FunctionalityDto() {
    }

    public FunctionalityDto(String id, String operatorId, String URL, FunctionalityType type) {
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
