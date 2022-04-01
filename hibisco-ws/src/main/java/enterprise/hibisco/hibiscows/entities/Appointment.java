package enterprise.hibisco.hibiscows.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idAppointment;

    @Getter @Setter private LocalDateTime dhAppointment;

    @Getter @Setter private boolean accepted;

    @Getter @Setter private Long fkDonator;

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
