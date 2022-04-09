package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@JsonDeserialize(as = Donator.class)
@SuppressWarnings("unused")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Getter @Setter private Long idUser;

    @NotBlank @Email
    @Getter @Setter private String email;

    @NotBlank
    // exige uma senha com no minimo 8 caracteres, cotendo maiúsculas e minúsculas, números e caracteres especiais
    //@Pattern(regexp = "/(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}/")
    private String password;

    @NotBlank
    @Getter @Setter private String phone;

    @Getter @Setter @NotNull
    private boolean authenticated;

    public User(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String recoverPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract ResponseEntity doRegister(Object user);

    public abstract ResponseEntity doLogin(Object user);

    public abstract ResponseEntity doLogoff(Long IdUser);

    public abstract String getDocument();
}
