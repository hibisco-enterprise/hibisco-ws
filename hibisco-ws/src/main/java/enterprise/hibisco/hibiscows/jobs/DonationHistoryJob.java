package enterprise.hibisco.hibiscows.jobs;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.DonationHistory;
import enterprise.hibisco.hibiscows.repositories.DonationHistoryRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private static final Logger logger = LoggerFactory.getLogger(DonationHistoryJob.class);

    @Scheduled(cron = "0 0 21 * * *", zone = "America/Sao_Paulo")
    public void doJob() {
        List<Appointment> appointments = appointment.getTodayAppointments();
        if (appointments.size() == 0) {
            logger.info("Sem agendamentos concluídos para hoje. Finalizando job...");
            return;
        }
        logger.info("iniciando job com {} agendamentos para armazenar em histórico", appointments.size());
        new Thread(() -> appointments.forEach(schedules -> {
            String nameHospital = hospitalRepository.findNameHospitalByIdUser(
                schedules.getHospitalAppointment().getHospital().getUser().getIdUser()
            );
            repository.save(
                DonationHistory.builder()
                    .dhScheduling(schedules.getDhAppointment())
                    .nameHospital(nameHospital)
                    .appointment(schedules)
                .build()
            );
        })).start();
    }

}
