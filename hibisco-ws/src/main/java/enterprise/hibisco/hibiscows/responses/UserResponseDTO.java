package enterprise.hibisco.hibiscows.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

public class UserResponseDTO {

    @Getter private String email;
    @Getter private String password;
    @Getter private String phone;
    @Getter private Optional<String> nameDonator;
    @Getter private Optional<String> cpf;
    @Getter private Optional<String> bloodType;
    @Getter private Optional<String> nameHospital;
    @Getter private Optional<String> cnpjHospital;
    @Getter private String address;
    @Getter private String neighborhood;
    @Getter private String city;
    @Getter private String uf;
    @Getter private String cep;
    @Getter private Integer number;

    //construtor doador
    public UserResponseDTO(String email,
                           String password,
                           String phone,
                           Optional<String> nameDonator,
                           Optional<String> cpf,
                           Optional<String> bloodType,
                           String address,
                           String neighborhood,
                           String city,
                           String uf,
                           String cep,
                           Integer number) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nameDonator = nameDonator;
        this.cpf = cpf;
        this.bloodType = bloodType;
        this.address = address;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.cep = cep;
        this.number = number;
    }

    // construtor hospital
    public UserResponseDTO(String email,
                           String password,
                           String phone,
                           Optional<String> nameHospital,
                           Optional<String> cnpjHospital,
                           String address,
                           String neighborhood,
                           String city,
                           String uf,
                           String cep,
                           Integer number) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nameHospital = nameHospital;
        this.cnpjHospital = cnpjHospital;
        this.address = address;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.cep = cep;
        this.number = number;
    }
}
