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

    @NotBlank
    @Getter @Setter private String name;

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
    @Getter @Setter private String password;

    @NotBlank
    @Getter @Setter private String phone;

    @Getter @Setter @NotNull
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
