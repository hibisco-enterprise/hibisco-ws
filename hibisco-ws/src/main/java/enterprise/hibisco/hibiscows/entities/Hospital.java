package enterprise.hibisco.hibiscows.entities;
import enterprise.hibisco.hibiscows.service.HospitalService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_hospital")
public class Hospital extends User{

    @Autowired @Transient
    private HospitalService hospitalService;

//    @Id //comentado, pois não há a possibilidade de manter o id nas subclasses
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Getter private Long idHospital;

    @NotBlank @NotNull @Min(8)
    @Getter @Setter private String nameHospital;

    @CNPJ
    @Setter private String cnpjHospital;

    @Override
    public ResponseEntity doRegister(User hospital) {
        return hospitalService.doRegister(hospital);
    }

    @Override
    public ResponseEntity doLogin(User user) {
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
