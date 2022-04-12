package enterprise.hibisco.hibiscows.entities;
import request.HospitalRequestDTO;
import enterprise.hibisco.hibiscows.service.HospitalService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_hospital")
@NoArgsConstructor
public class Hospital extends User {

    @Autowired @Transient
    private HospitalService hospitalService;

//    @Id //comentado, pois não há a possibilidade de manter o id nas subclasses
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Getter private Long idHospital;

    @NotBlank
    @Getter @Setter private String nameHospital;

    @NotBlank @CNPJ
    @Setter private String cnpjHospital;

    @NotNull
    @Getter @Setter private Long fkAddress;

    public Hospital(String email,
                    String password,
                    String phone,
                    String nameHospital,
                    String cnpjHospital,
                    Long fkAddress) {
        super(email, password, phone);
        this.nameHospital = nameHospital;
        this.cnpjHospital = cnpjHospital;
        this.fkAddress = fkAddress;
    }

    @Override
    public ResponseEntity doRegister(Object hospital) {
        return hospitalService.doRegister((HospitalRequestDTO) hospital);
    }

    @Override
    public ResponseEntity doLogin(Object user) {
        return hospitalService.doLogin((HospitalRequestDTO) user);
    }

    @Override
    public ResponseEntity doLogoff(Long login) {
        return hospitalService.doLogoff(login);
    }

    @Override
    public String getDocument() {
        return cnpjHospital;
    }

}
