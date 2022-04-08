package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class HospitalAppointmentService {

    @Autowired
    private HospitalAppointmentRepository appointmentRepository;

    public ResponseEntity<List<HospitalAppointment>> getAvaliableDays(Long idHospital) {
        if (appointmentRepository.findById(idHospital).stream().findAny().isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                appointmentRepository.getAppointmentDaysByIdHospital(idHospital)
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
        //TODO: buscar data a remover e executar o delete
        return null;
    }

}
