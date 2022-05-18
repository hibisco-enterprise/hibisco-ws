package enterprise.hibisco.hibiscows.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@Builder
@SuppressWarnings("unused")
public class DonatorLoginRequestDTO {

    @Email
    @Getter private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter private String password;


    public DonatorLoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
