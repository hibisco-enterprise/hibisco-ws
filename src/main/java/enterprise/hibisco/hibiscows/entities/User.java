package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

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

    @NotBlank
    @Getter @Setter private String name;

    @NotBlank @Email
    @Getter @Setter private String email;

    @Column(length = 50 * 1024 * 1024) // 50Mb
    @JsonIgnore
    @Nullable
    private byte[] photo;

    @Length(min = 11, max = 18,
        message = "Documento inválido, verifique as pontuações, espaços e zeros à esquerda"
    )
    @Pattern(regexp =
        "^([0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}|[0-9]{2}" +
        "\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2})$",
        message = "Documento inválido. Verifique a pontuação e a quantidade de caracteres!")
    @NotBlank
    @Getter @Setter private String documentNumber;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%&*+=^~/?|<>,.;:])(?=.*\\d)(?=.*[a-z]).{8,16}$",
    message = "Senha inválida. A senha deve possuir no mínimo 8 caracteres, " +
              "contendo letras maiúsculas e minúsculas, números e caracteres especiais!")
    @Getter @Setter private String password;

    @NotBlank
    @Getter @Setter private String phone;

    @Getter @Setter
    private boolean authenticated;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="fk_address")
    @Getter @Setter private AddressData address;

    @Builder
    public User(String name,
                String email,
                String documentNumber,
                String password,
                String phone,
                AddressData address) {
        this.name = name;
        this.email = email;
        this.documentNumber = documentNumber;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

}
