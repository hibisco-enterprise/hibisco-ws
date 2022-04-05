package enterprise.hibisco.hibiscows.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idAppointment;

    @NotBlank @NotNull @FutureOrPresent
    @Getter @Setter private LocalDateTime dhAppointment;

    @Getter @Setter @NotNull
    private boolean accepted;

    @NotBlank @NotBlank
    @Getter @Setter private Long fkDonator;

    @NotBlank @NotBlank
    @Getter @Setter private Long fkHospital;

    public Appointment(Long idAppointment,
                       LocalDateTime dhAppointment,
                       Long fkDonator,
                       Long fkHospital) {
        this.idAppointment = idAppointment;
        this.dhAppointment = dhAppointment;
        this.fkDonator = fkDonator;
        this.fkHospital = fkHospital;
    }
}
