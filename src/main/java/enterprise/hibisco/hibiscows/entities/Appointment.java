package enterprise.hibisco.hibiscows.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_appointment")
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long idAppointment;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @NotNull @FutureOrPresent
    @Getter @Setter private LocalDateTime dhAppointment;

    @Getter @Setter private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_donator")
    @Getter @Setter private Donator donator;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_hospital")
    @Getter @Setter private Hospital hospital;

    public Appointment(LocalDateTime dhAppointment,
                       Donator donator,
                       Hospital hospital) {
        this.dhAppointment = dhAppointment;
        this.donator = donator;
        this.hospital = hospital;
    }
}
