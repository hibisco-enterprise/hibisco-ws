package enterprise.hibisco.hibiscows.entities;
import enterprise.hibisco.hibiscows.responses.UserResponseDTO;
import enterprise.hibisco.hibiscows.service.HospitalService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_hospital")
public class Hospital extends User {

    @Autowired @Transient
    private HospitalService hospitalService;

//    @Id //comentado, pois não há a possibilidade de manter o id nas subclasses
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Getter private Long idHospital;

    @NotBlank @NotNull @Min(8)
    @Getter @Setter private String nameHospital;

    @NotBlank @NotNull @CNPJ @Min(14) @Max(18)
    @Setter private String cnpjHospital;

    public Hospital(String email,
                    String password,
                    String phone,
                    String nameHospital,
                    String cnpjHospital) {
        super(email, password, phone);
        this.nameHospital = nameHospital;
        this.cnpjHospital = cnpjHospital;
    }

    @Override
    public ResponseEntity doRegister(UserResponseDTO hospital) {
        return hospitalService.doRegister(hospital);
    }

    @Override
    public ResponseEntity doLogin(UserResponseDTO user) {
        return hospitalService.doLogin(user);
    }

    @Override
    public ResponseEntity doLogoff(String login) {
        return hospitalService.doLogoff(login);
    }

    @Override
    public String getDocument() {
        return cnpjHospital;
    }

}
