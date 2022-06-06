package enterprise.hibisco.hibiscows.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDTO {

    @NotNull
    @Getter @Setter private Long idAppointment;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Getter @Setter private LocalDateTime dhAppointment;

    @NotBlank
    @Getter @Setter private Boolean accepted;

    @NotNull
    @Getter @Setter private Long idDonator;

    @NotBlank
    @Getter @Setter private String bloodType;

    @NotBlank
    @Getter @Setter private String name;

    @NotBlank
    @Getter @Setter private String email;

    @NotBlank
    @Getter @Setter private String documentNumber;

    @NotBlank
    @Getter @Setter private String phone;

    @NotNull
    @Getter @Setter private Long idAddress;

    @NotBlank
    @Getter @Setter private String address;

    @NotBlank
    @Getter @Setter private String neighborhood;

    @NotBlank
    @Getter @Setter private String city;

    @NotBlank
    @Getter @Setter private String uf;

    @NotBlank
    @Getter @Setter private String cep;

    @NotNull
    @Getter @Setter private Integer number;

    @NotNull
    @Getter @Setter private Long idHospital;

}
