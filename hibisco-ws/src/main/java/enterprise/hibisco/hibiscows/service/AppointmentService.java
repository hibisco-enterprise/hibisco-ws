package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.AppointmentRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

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
        Optional<Donator> donator = donatorRepository.findById(idDonator);

        if (appointmentDay.isPresent() && donator.isPresent()) {
            Appointment appointment = repository.save(
                new Appointment(
                    appointmentDay.get().getDhAvaliable(),
                    donator.get(),
                    appointmentDay.get()
                )
            );
            return status(CREATED).body(appointment);
        }
        return status(NOT_FOUND).build();
    }

    public ResponseEntity<List<Appointment>> getAppointmentDays(Long idDonator) {

        List<Appointment> appointmentDays = repository.findByDonator(
            idDonator
        );

        if (!appointmentDays.isEmpty()) {
            return status(OK).body(appointmentDays);
        }

        return status(NOT_FOUND).build();
    }

    public ResponseEntity<?> cancelAppointmentDay(Long idAppointment) {
        if (repository.existsById(idAppointment)) {
            repository.deleteById(idAppointment);
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    public List<Appointment> getTodayAppointments() {
        List<Appointment> appointments = repository.findAll();
        List<Appointment> todayAppointments = new ArrayList<>();

        new Thread(() -> {
            for (Appointment a : appointments) {
                if (a.getDhAppointment().toLocalDate().isEqual(LocalDate.now()) && a.isAccepted()) {
                    todayAppointments.add(a);
                }
            }
        }).start();

        return todayAppointments;
    }

}
