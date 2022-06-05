package enterprise.hibisco.hibiscows.rest.mapbox;

import lombok.Getter;

public class LatLongDTO {
    @Getter
    private Double latitude;

    @Getter
    private Double longitude;

    public LatLongDTO(Double longitude, Double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
