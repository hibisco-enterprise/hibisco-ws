package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.request.AvaliableDaysWrapperRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class HospitalAppointmentService {

    @Autowired
    private HospitalAppointmentRepository repository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public ResponseEntity<List<HospitalAppointment>> getAvaliableDays(Long idHospital) {
        List<HospitalAppointment> appointments = repository.findByHospital(idHospital);
        if (appointments.isEmpty()) {
            return status(NO_CONTENT).build();
        }
        return status(OK).body(appointments);
    }

    public ResponseEntity<?> setAvaliableDays(
        Long idHospital,
        AvaliableDaysWrapperRequestDTO avaliableDays
    ) {
        Optional<Hospital> hospital = hospitalRepository.findById(idHospital);

        if (hospital.isPresent()) {
            avaliableDays.getAvaliableDays().forEach(day ->
                repository.save(
                    new HospitalAppointment(
                        day,
                        hospital.get()
                    )
                )
            );
            return status(CREATED).build();
        }

        return status(NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteAvaliableDay(
        Long idHospitalAppointment
    ) {
        Optional<HospitalAppointment> appointment = repository.findById(idHospitalAppointment);
        if (appointment.isPresent()) {
            repository.deleteById(appointment.get().getIdHospitalAppointment());
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    public ResponseEntity<Appointment> acceptAppointmentDay(Long idAppointment) {
        if (repository.existsById(idAppointment)) {
            repository.acceptAppointmentDay(idAppointment);
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }


}
