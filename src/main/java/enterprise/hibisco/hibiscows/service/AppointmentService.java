package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
