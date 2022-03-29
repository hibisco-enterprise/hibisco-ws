package enterprise.hibisco.hibiscows.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
@AllArgsConstructor
@SuppressWarnings("unused")
public class UserResponseDTO {

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
    public UserResponseDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String recoverPassword() {
        return password;
    }
}