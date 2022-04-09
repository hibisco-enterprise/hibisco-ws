package enterprise.hibisco.hibiscows.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@SuppressWarnings("unused")
public class HospitalResponseDTO {

    @Getter private final String email;
    private final String password;
    @Getter private String phone;
    @Getter private String nameHospital;
    @Getter private String cnpjHospital;
    @Getter private String address;
    @Getter private String neighborhood;
    @Getter private String city;
    @Getter private String uf;
    @Getter private String cep;
    @Getter private Integer number;

    public HospitalResponseDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String recoverPassword() {
        return password;
    }
}
