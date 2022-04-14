package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class HospitalAppointmentService {

    @Autowired
    private HospitalAppointmentRepository appointmentRepository;

    public ResponseEntity<List<HospitalAppointment>> getAvaliableDays(Long idHospital) {
        if (appointmentRepository.findById(idHospital).stream().findAny().isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                appointmentRepository.findByFkHospital(idHospital)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> setAvaliableDays(
        Long idHospital,
        List<LocalDateTime> avaliableDays
    ) {
        if (appointmentRepository.existsByFkHospital(idHospital)) {
            avaliableDays.forEach(days ->
                appointmentRepository.save(
                    new HospitalAppointment(
                        days,
                        idHospital
                    )
                )
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteAvaliableDay(
        Long idHospital,
        LocalDateTime avaliableDay
    ) {
        if (appointmentRepository.existsByFkHospital(idHospital)) {
            Optional<HospitalAppointment> hospitalAppointment =
                appointmentRepository.findByDhAvaliableAndFkHospital(avaliableDay, idHospital);

            if (hospitalAppointment.isPresent()) {
                appointmentRepository.deleteById(hospitalAppointment.get().getIdHospitalAppointment());
                return ResponseEntity.status(HttpStatus.OK).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                "Data agendada não encontrada para este hospital."
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamentos não encontrados para este hospital.");
    }

}
