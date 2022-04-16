package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import enterprise.hibisco.hibiscows.request.AvaliableDaysWrapperRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class HospitalAppointmentService {

    @Autowired
    private HospitalAppointmentRepository repository;

    public ResponseEntity<List<HospitalAppointment>> getAvaliableDays(Long idHospital) {
        List<HospitalAppointment> appointments = repository.findByFkHospital(idHospital);
        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(appointments);
    }

    public ResponseEntity<?> setAvaliableDays(
        Long idHospital,
        AvaliableDaysWrapperRequestDTO avaliableDays
    ) {
        avaliableDays.getAvaliableDays().forEach(day ->
            repository.save(
                new HospitalAppointment(
                    day,
                    idHospital
                )
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> deleteAvaliableDay(
        Long idHospitalAppointment
    ) {
        Optional<HospitalAppointment> appointment = repository.findById(idHospitalAppointment);
        if (appointment.isPresent()) {
            repository.deleteById(appointment.get().getIdHospitalAppointment());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
