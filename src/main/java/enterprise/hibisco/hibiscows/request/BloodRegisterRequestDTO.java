package enterprise.hibisco.hibiscows.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BloodRegisterRequestDTO {
    @Getter @Setter
    private String documentNumber;
    @Getter @Setter
    private List<BloodTypeWrapperDTO> bloodStock;
}
