package flightsearch.dtos;

public class FlightResponseDto {
    private FlightDto flightDto;
    private FlightDetailsDto flightDetailsDto;

    public FlightDto getFlightDto() {
        return flightDto;
    }

    public void setFlightDto(FlightDto flightDto) {
        this.flightDto = flightDto;
    }

    public FlightDetailsDto getFlightDetailsDto() {
        return flightDetailsDto;
    }

    public void setFlightDetailsDto(FlightDetailsDto flightDetailsDto) {
        this.flightDetailsDto = flightDetailsDto;
    }
}
