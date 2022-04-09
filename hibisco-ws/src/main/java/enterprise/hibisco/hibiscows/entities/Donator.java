package enterprise.hibisco.hibiscows.entities;
import enterprise.hibisco.hibiscows.responses.DonatorResponseDTO;
import enterprise.hibisco.hibiscows.service.DonatorService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_donator")
@NoArgsConstructor
public class Donator extends User {

    @Autowired @Transient
    private DonatorService donatorService;

//    @Id //comentado, pois não há a possibilidade de manter o id nas subclasses
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Getter private Long idDonator;

    @NotBlank
    @Getter @Setter private String nameDonator;

    @NotBlank @CPF
    @Setter private String cpf;

    @NotBlank
    @Getter @Setter private String bloodType;

    @NotNull
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
    public ResponseEntity doRegister(Object donator) {
        return donatorService.doRegister((DonatorResponseDTO) donator);
    }

    @Override
    public ResponseEntity doLogin(Object user) {
        return donatorService.doLogin((DonatorResponseDTO) user);
    }

    @Override
    public ResponseEntity doLogoff(Long IdUser) {
        return donatorService.doLogoff(IdUser);
    }

    @Override
    public String getDocument() {
        return cpf;
    }

}
