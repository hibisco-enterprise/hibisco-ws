package enterprise.hibisco.hibiscows.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_donation_history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BloodStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idBloodStock;

    @Length(min = 2, max = 3, message = "Tipo sanguíneo inválido.")
    @Getter @Setter private String BloodType;

    @Range(min = 0, max = (long) 100.0, message = "Insira um número válido entre 0% e 100%")
    @Getter @Setter private Double percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_hospital")
    @Getter @Setter private Hospital hospital;

}
