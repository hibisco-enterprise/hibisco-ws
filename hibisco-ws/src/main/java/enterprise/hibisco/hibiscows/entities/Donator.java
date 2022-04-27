package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import enterprise.hibisco.hibiscows.request.DonatorRequestDTO;
import enterprise.hibisco.hibiscows.service.DonatorService;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_donator")
@NoArgsConstructor
public class Donator extends User {

    @Autowired @Transient
    private DonatorService donatorService;

    @NotBlank
    @Getter @Setter private String nameDonator;

    @NotBlank @CPF
    @Getter @Setter private String cpf;

    @NotBlank
    @Getter @Setter private String bloodType;

    @Getter @Setter private Long fkAddress;

    public Donator(String email,
                   String password,
                   String phone,
                   String nameDonator,
                   String cpf,
                   String bloodType,
                   Long fkAddress) {
        super(email, password, phone);
        this.nameDonator = nameDonator;
        this.cpf = cpf;
        this.bloodType = bloodType;
        this.fkAddress = fkAddress;
    }

    @Override
    public ResponseEntity<?> doRegister(Object donator) {
        return donatorService.doRegister((DonatorRequestDTO) donator);
    }

    @Override
    public ResponseEntity<?> doLogin(Object donator) {
        return donatorService.doLogin((DonatorRequestDTO) donator);
    }

    @Override
    public ResponseEntity<?> doLogoff(Long IdUser) {
        return donatorService.doLogoff(IdUser);
    }

}
