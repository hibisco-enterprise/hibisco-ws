package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import enterprise.hibisco.hibiscows.request.HospitalRequestDTO;
import enterprise.hibisco.hibiscows.service.HospitalService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_hospital")
@NoArgsConstructor
public class Hospital extends User {

    @Autowired @Transient
    private HospitalService hospitalService;

    @NotBlank
    @Getter @Setter private String nameHospital;

    @NotBlank @CNPJ
    @Getter @Setter private String cnpjHospital;

    public Hospital(String email,
                    String password,
                    String phone,
                    AddressData address,
                    String nameHospital,
                    String cnpjHospital) {
        super(email, password, phone, address);
        this.nameHospital = nameHospital;
        this.cnpjHospital = cnpjHospital;
    }

    @Override
    public ResponseEntity<?> doRegister(Object hospital) {
        return hospitalService.doRegister((Hospital) hospital);
    }

    @Override
    public ResponseEntity<?> doLogin(Object hospital) {
        return hospitalService.doLogin((HospitalRequestDTO) hospital);
    }

    @Override
    public ResponseEntity<?> doLogoff(Long login) {
        return hospitalService.doLogoff(login);
    }


}
