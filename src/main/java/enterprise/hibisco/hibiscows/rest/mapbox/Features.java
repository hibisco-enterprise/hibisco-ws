package enterprise.hibisco.hibiscows.rest.mapbox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class Features {
    @Getter @Setter
    List<Double> center;
}
