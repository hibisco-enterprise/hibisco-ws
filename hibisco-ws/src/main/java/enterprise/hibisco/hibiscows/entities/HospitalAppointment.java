package enterprise.hibisco.hibiscows.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_hospital_appointment")
public class HospitalAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idHospitalAppointment;

    @NotNull @FutureOrPresent
    @Getter @Setter private LocalDateTime dhAvaliable;

    @NotNull
    @Getter @Setter private Long fkHospital;

    public HospitalAppointment(LocalDateTime dhAvaliable, Long fkHospital) {
        this.dhAvaliable = dhAvaliable;
        this.fkHospital = fkHospital;
    }
}
