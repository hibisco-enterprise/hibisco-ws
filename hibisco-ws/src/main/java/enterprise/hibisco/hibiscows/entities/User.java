package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
@Data
@Builder
@Table(name = "tb_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Getter @Setter private Long idUser;

    @NotBlank @Email
    @Getter @Setter private String email;

    @Length(
            min = 11, max = 18,
            message = "Documento inválido, verifique as pontuações, espaços e zeros à esquerda"
    )
    @NotBlank
    @Getter @Setter private String documentNumber;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Pattern(regexp = "/(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[\\da-zA-Z$*&@#]{8,}/",
//             message = "Senha fraca! A senha deve possuir 8 caracteres, letras maiúsculas " +
//                       "e minúsculas, números e caracteres especiais."
//    )
    @Getter @Setter private String password;

    @NotBlank
    @Getter @Setter private String phone;

    @Getter @Setter @NotNull
    private boolean authenticated;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="fk_address")
    @Getter @Setter private AddressData address;

    public User(String email, String documentNumber, String password, String phone, AddressData address) {
        this.email = email;
        this.documentNumber = documentNumber;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }
}
