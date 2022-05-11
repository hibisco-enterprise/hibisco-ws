package enterprise.hibisco.hibiscows.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_donation_history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idDonationHistory;

    @FutureOrPresent
    @Getter @Setter private LocalDateTime dhScheduling;

    @NotBlank
    @Getter @Setter private String nameHospital;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_appointment")
    @Getter @Setter private Appointment appointment;

    @Builder
    public DonationHistory(LocalDateTime dhScheduling,
                           String nameHospital,
                           Appointment appointment) {
        this.dhScheduling = dhScheduling;
        this.nameHospital = nameHospital;
        this.appointment = appointment;
    }
}
