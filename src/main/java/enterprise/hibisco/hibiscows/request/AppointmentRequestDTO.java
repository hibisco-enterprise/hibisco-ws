package enterprise.hibisco.hibiscows.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppointmentRequestDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @NotNull
    @FutureOrPresent
    @Getter @Setter private LocalDateTime dhAppointment;

    @NotNull
    @Getter @Setter private Long FkDonator;

    @NotNull
    @Getter @Setter private Long FkHospital;

}
