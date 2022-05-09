package enterprise.hibisco.hibiscows.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@SuppressWarnings("unused")
@Table(name = "tb_donator")
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Donator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idDonator;

    @NotBlank
    @Getter @Setter private String bloodType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user")
    @Getter @Setter private User fkUser;

    public Donator(String bloodType, User fkUser) {
        this.bloodType = bloodType;
        this.fkUser = fkUser;
    }
}
