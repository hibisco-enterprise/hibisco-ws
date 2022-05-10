package enterprise.hibisco.hibiscows.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tb_donation_history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idDonationHistory;

    @FutureOrPresent
    @Getter @Setter private LocalDateTime dhScheduling;

    @Length(min = 2, max = 3, message = "Tipo sanguíneo inválido!")
    @Getter @Setter private String bloodType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_appointment")
    @Getter @Setter private Appointment appointment;

    public DonationHistory(LocalDateTime dhScheduling,
                           String bloodType,
                           Appointment appointment) {
        this.dhScheduling = dhScheduling;
        this.bloodType = bloodType;
        this.appointment = appointment;
    }
}
