package enterprise.hibisco.hibiscows.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DonatorResponseDTO {

    @Getter private String email;
    @Getter private String password;
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

}
