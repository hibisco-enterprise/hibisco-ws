package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.AppointmentRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private HospitalAppointmentRepository hospitalAppointmentRepository;

    public ResponseEntity<?> setAppointmentDay(Appointment appointment) {
        Optional<HospitalAppointment> appointmentDay = hospitalAppointmentRepository.findById(
            appointment.getFkAppointmentHospital()
        );
        if (appointmentDay.isPresent()) {
            repository.save(
                new Appointment(
                    appointmentDay.get().getDhAvaliable(),
                    appointment.getFkDonator(),
                    appointment.getFkHospital(),
                    appointment.getFkAppointmentHospital()
                )
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<List<Appointment>> getAppointmentDays(Long idDonator, Long idHospital) {

        List<Appointment> appointmentDays = repository.findByFkDonatorAndFkHospital(
            idDonator,
            idHospital
        );

        if (!appointmentDays.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(appointmentDays);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
