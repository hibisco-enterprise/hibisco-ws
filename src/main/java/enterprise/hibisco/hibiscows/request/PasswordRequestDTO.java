package enterprise.hibisco.hibiscows.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("updatePassword")
public class PasswordRequestDTO {

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter @Setter private String password;

}
