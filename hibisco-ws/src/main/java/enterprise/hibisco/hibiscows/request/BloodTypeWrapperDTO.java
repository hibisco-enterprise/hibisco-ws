package enterprise.hibisco.hibiscows.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class BloodTypeWrapperDTO {
    public BloodTypeWrapperDTO(String bloodType, Double percentage) {
        this.bloodType = bloodType;
        this.percentage = percentage;
    }

    @Getter @Setter
    private String bloodType;
    @Getter @Setter
    private Double percentage;
    @Getter @Setter
    private String documentNumber;
}
