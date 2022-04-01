package enterprise.hibisco.hibiscows.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_hospital_appointment")
@AllArgsConstructor
public class HospitalAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idHospitalAppointment;

    @Getter @Setter private LocalDateTime dhAvaliable;

    @Getter @Setter private Long fkHospital;

}
