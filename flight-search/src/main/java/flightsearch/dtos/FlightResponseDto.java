package flightsearch.dtos;

public class FlightResponseDto {
    private FlightDto flightDto;
    private OperatorDto operatorDto;

    public FlightResponseDto() {
    }

    public FlightResponseDto(FlightDto flightDto, OperatorDto operatorDto) {
        this.flightDto = flightDto;
        this.operatorDto = operatorDto;
    }

    public OperatorDto getOperatorDto() {
        return operatorDto;
    }

    public void setOperatorDto(OperatorDto operatorDto) {
        this.operatorDto = operatorDto;
    }

    public FlightDto getFlightDto() {
        return flightDto;
    }

    public void setFlightDto(FlightDto flightDto) {
        this.flightDto = flightDto;
    }

}
