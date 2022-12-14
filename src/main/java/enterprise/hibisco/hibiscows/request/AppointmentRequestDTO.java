package enterprise.hibisco.hibiscows.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    @FutureOrPresent
    @Getter @Setter private LocalDateTime dhAppointment;

    @NotNull
    @Getter @Setter private Long FkDonator;

    @NotNull
    @Getter @Setter private Long FkHospital;

}
