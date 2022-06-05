package enterprise.hibisco.hibiscows.response;

import enterprise.hibisco.hibiscows.entities.AddressData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {

    @Getter @Setter private Integer statusCode;
    @Getter @Setter private Optional<AddressData> addressData;

}
