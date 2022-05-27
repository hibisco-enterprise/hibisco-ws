package enterprise.hibisco.hibiscows.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("unused")
public class HospitalLoginRequestDTO {

    @Getter private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter private String password;

    public HospitalLoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
