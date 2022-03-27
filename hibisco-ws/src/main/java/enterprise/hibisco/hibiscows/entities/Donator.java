package enterprise.hibisco.hibiscows.entities;
import enterprise.hibisco.hibiscows.responses.DonatorResponseDTO;
import enterprise.hibisco.hibiscows.service.DonatorService;
import lombok.Getter;
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
public class Donator extends User{

    @Autowired @Transient
    private DonatorService donatorService;

//    @Id //comentado, pois não há a possibilidade de manter o id nas subclasses
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Getter private Long idDonator;

    @NotNull @NotBlank @Min(3)
    @Getter @Setter private String nameDonator;

    @NotNull @NotBlank @CPF
    @Setter private String cpf;

    @NotNull @NotBlank @Min(2) @Max(3)
    @Getter @Setter private String bloodType;

    @NotNull @NotBlank
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
    public ResponseEntity doRegister(DonatorResponseDTO donator) {
        return donatorService.doRegister(donator);
    }

    @Override
    public ResponseEntity doLogin(User user) {
        return donatorService.doLogin(user);
    }

    @Override
    public ResponseEntity doLogoff(String email) {
        return donatorService.doLogoff(email);
    }

    @Override
    public String getDocument() {
        return cpf;
    }

}
