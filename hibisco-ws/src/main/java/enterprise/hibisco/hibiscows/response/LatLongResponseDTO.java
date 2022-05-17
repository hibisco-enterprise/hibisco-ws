package enterprise.hibisco.hibiscows.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class LatLongResponseDTO {
    @Getter @Setter
    private Double latitude;
    @Getter @Setter
    private Double longitude;

}
