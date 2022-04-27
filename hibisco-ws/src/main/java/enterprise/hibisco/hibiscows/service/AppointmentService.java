package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.AppointmentRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private DonatorRepository donatorRepository;

    @Autowired
    private HospitalAppointmentRepository hospitalAppointmentRepository;

    public ResponseEntity<Appointment> setAppointmentDay(Long fkAppointmentHospital, Long idDonator) {
        Optional<HospitalAppointment> appointmentDay = hospitalAppointmentRepository.findById(
                fkAppointmentHospital
        );

        if (appointmentDay.isPresent() && donatorRepository.existsById(idDonator)) {
            Appointment appointment = repository.save(
                new Appointment(
                    appointmentDay.get().getDhAvaliable(),
                    idDonator,
                    appointmentDay.get().getFkHospital(),
                    fkAppointmentHospital
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

    public ResponseEntity<?> cancelAppointmentDay(Long idAppointment) {
        if (repository.existsById(idAppointment)) {
            repository.deleteById(idAppointment);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
