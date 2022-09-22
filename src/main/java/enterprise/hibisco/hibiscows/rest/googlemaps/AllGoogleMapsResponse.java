package enterprise.hibisco.hibiscows.rest.googlemaps;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AllGoogleMapsResponse {
    @Getter
    @Setter
    Results[] results;
}
