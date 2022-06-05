package enterprise.hibisco.hibiscows.jobs;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.DonationHistory;
import enterprise.hibisco.hibiscows.manager.FilaObj;
import enterprise.hibisco.hibiscows.manager.ListaObj;
import enterprise.hibisco.hibiscows.repositories.DonationHistoryRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class DonationHistoryJob {

    @Autowired
    private AppointmentService appointment;

    @Autowired
    private DonationHistoryRepository repository;

    @Autowired
    private HospitalRepository hospitalRepository;

    private FilaObj<Appointment> appointmentsList;

    private static final Logger logger = LoggerFactory.getLogger(DonationHistoryJob.class);

    @Scheduled(cron = "0 0 21 * * *", zone = "America/Sao_Paulo")
    public void doJob() {
        List<Appointment> appointments = appointment.getTodayAppointments();
        appointmentsList = new FilaObj<>(appointments.size());
        appointments.forEach(appointment -> appointmentsList.insert(appointment));
        if (appointmentsList.getTamanho() == 0) {
            logger.info("Sem agendamentos concluídos para hoje. Finalizando job...");
            return;
        }
        logger.info("iniciando job com {} agendamentos para armazenar em histórico",
                appointmentsList.getTamanho());

        for (int i = 0; i < appointmentsList.getTamanho(); i++) {
            Appointment currentAppointment = appointmentsList.poll();
            String nameHospital = hospitalRepository.findNameHospitalByIdUser(
                    currentAppointment.getHospitalAppointment().getHospital().getUser().getIdUser()
            );
            repository.save(
                DonationHistory.builder()
                    .dhScheduling(currentAppointment.getDhAppointment())
                    .nameHospital(nameHospital)
                    .appointment(currentAppointment)
                .build()
            );
        }
    }
}
