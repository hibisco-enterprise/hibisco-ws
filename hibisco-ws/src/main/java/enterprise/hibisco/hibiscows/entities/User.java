package enterprise.hibisco.hibiscows.entities;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.*;
import java.util.Locale;

@SuppressWarnings("unused")
public abstract class User {

    @NotNull @NotBlank @Email
    @Getter @Setter private String email;

    @NotNull @NotBlank @Min(8)
    @Pattern(regexp = "/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$/") // exige uma senha com no minimo 8 caracteres, cotendo maiúsculas e minúsculas, números e caracteres especiais
    private String password;

    @Size(max = 14)
    @Getter @Setter private String phone;

    @NotNull @NotBlank
    @Getter @Setter private String address; // por enquanto está sendo gerado automaticamente

    @NotNull
    @Getter @Setter  private boolean authenticated;

    public User() {
        this.address = new Faker(new Locale("pt-BR")).address().fullAddress();
    }

    public boolean authenticate(User user) {
        return this.email.equals(user.getEmail()) &&
               this.password.equals(user.recuperarPassword());
    }

    public String recuperarPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract ResponseEntity doRegister(User user);

    public abstract ResponseEntity doLogin(User user);

    public abstract ResponseEntity doLogoff(String email);

    public abstract String getDocument();
}
