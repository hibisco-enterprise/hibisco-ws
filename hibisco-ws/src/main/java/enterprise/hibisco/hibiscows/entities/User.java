package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@SuppressWarnings("unused")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Getter @Setter private Long idUser;

    @NotBlank @Email
    @Getter @Setter private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // exige uma senha com no minimo 8 caracteres, cotendo maiúsculas e minúsculas, números e caracteres especiais
    @Pattern(regexp = "/(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}/",
             message = "Senha fraca! A senha deve possuir 8 caracteres, letras maiúsculas" +
                     " e minúsculas, números e caracteres especiais."
    )
    @Getter @Setter private String password;

    @NotBlank
    @Getter @Setter private String phone;

    @Getter @Setter @NotNull
    private boolean authenticated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_address")
    @Getter @Setter private AddressData address;

    public User(String email,
                String password,
                String phone,
                AddressData address) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public abstract ResponseEntity<?> doRegister(Object user);

    public abstract ResponseEntity<?> doLogin(Object user);

    public abstract ResponseEntity<?> doLogoff(Long IdUser);

}
