package enterprise.hibisco.hibiscows.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_hospital_appointment")
@AllArgsConstructor
public class HospitalAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idHospitalAppointment;

    @NotBlank @FutureOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter private LocalDateTime dhAvaliable;

    @Getter @Setter private Long fkHospital;

}
