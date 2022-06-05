package enterprise.hibisco.hibiscows.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonRootName("AvaliableDaysResponse")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliableDaysResponseDTO {

    @Getter @Setter private Long idHospitalAppointment;

    @JsonProperty("day")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Getter @Setter private LocalDateTime avaliableDay;

    @Getter @Setter private Long idHospital;
}
