package enterprise.hibisco.hibiscows.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")

public class DonatorResponseDTO {

    @Getter private String email;
    private String password;
    @Getter private String phone;
    @Getter private String nameDonator;
    @Getter private String cpf;
    @Getter private String bloodType;
    @Getter private String address;
    @Getter private String neighborhood;
    @Getter private String city;
    @Getter private String uf;
    @Getter private String cep;
    @Getter private Integer number;

    //Construtor Login
    public DonatorResponseDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String recoverPassword() {
        return password;
    }
}
