package enterprise.hibisco.hibiscows.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_appointment")
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idAppointment;

    @NotNull @FutureOrPresent
    @Getter @Setter private LocalDateTime dhAppointment;

    @Getter @Setter @NotNull
    private boolean accepted;

    @NotNull
    @Getter @Setter private Long fkDonator;

    @NotNull
    @Getter @Setter private Long fkHospital;

    @NotNull
    @Getter @Setter private Long fkAppointmentHospital;

    public Appointment(LocalDateTime dhAppointment,
                       Long fkDonator,
                       Long fkHospital,
                       Long fkAppointmentHospital) {
        this.dhAppointment = dhAppointment;
        this.fkDonator = fkDonator;
        this.fkHospital = fkHospital;
        this.fkAppointmentHospital = fkAppointmentHospital;
    }

   //  {
   //     "dhAppointment": "2022-05-16T10:00:00",
   //         "accepted": false,
   //         "fkDonator": 5,
   //         "fkHospital": 1,
   //         "fkAppointmentHospital": 2
    // }
}
