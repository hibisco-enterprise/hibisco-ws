package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.AppointmentRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import enterprise.hibisco.hibiscows.response.AppointmentResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getTodayAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
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
