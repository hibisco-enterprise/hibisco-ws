package enterprise.hibisco.hibiscows.rest.mapbox;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AllMapBoxResponse {
    @Getter @Setter
    List<Features> features;
}
