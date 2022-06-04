package enterprise.hibisco.hibiscows.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class BloodTypeWrapperDTO {
    @Getter @Setter
    private String bloodType;
    @Getter @Setter
    private Double percentage;
}
