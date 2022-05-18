package enterprise.hibisco.hibiscows.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
public class HospitalRequestDTO {

    @Getter private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter private String password;

    @Getter private String phone;

    @Getter private String nameHospital;

    @CNPJ @Getter private String cnpjHospital;

    @Getter private String address;

    @Getter private String neighborhood;

    @Getter private String city;

    @Getter private String uf;

    @Getter private String cep;

    @Getter private Integer number;

    public HospitalRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String recoverPassword() {
        return password;
    }
}
