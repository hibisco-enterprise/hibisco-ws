package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.concurrent.ThreadLocalRandom;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_donator")
public class Donator extends User{

    @Autowired @Transient
    private DonatorService donatorService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long idDonator;

    @NotNull @NotBlank
    @Getter @Setter private String name;

    @NotNull @NotBlank @CPF
    @Getter @Setter private String cpf;

    @NotNull @NotBlank @Min(2) @Max(3)
    @Getter @Setter private String bloodType;

    @Override
    public ResponseEntity doRegister(User donator) {
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
