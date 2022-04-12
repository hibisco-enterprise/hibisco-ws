package enterprise.hibisco.hibiscows.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")

public class DonatorRequestDTO {

    @Email
    @Getter private String email;

    private String password;

    @Getter private String phone;

    @Getter private String nameDonator;

    @CPF
    @Getter private String cpf;
    @Getter private String bloodType;
    @Getter private String address;
    @Getter private String neighborhood;
    @Getter private String city;
    @Getter private String uf;
    @Getter private String cep;
    @Getter private Integer number;

    public DonatorRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String recoverPassword() {
        return password;
    }
}
