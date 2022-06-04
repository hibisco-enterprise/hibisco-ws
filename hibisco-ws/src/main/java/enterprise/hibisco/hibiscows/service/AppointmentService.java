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

    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DonatorRepository donatorRepository;

    @Autowired
    private HospitalAppointmentRepository hospitalAppointmentRepository;

    public ResponseEntity<Void> setAppointmentDay(Long fkAppointmentHospital, Long idDonator) {
        Optional<HospitalAppointment> appointmentDay = hospitalAppointmentRepository.findById(
                fkAppointmentHospital
        );
        Optional<Donator> donator = donatorRepository.findById(idDonator);


        if (appointmentDay.isPresent() && donator.isPresent()) {
            appointmentRepository.save(
                new Appointment(
                    appointmentDay.get().getDhAvaliable(),
                    donator.get(),
                    appointmentDay.get()
                )
            );

            return status(CREATED).build();
        }
        return status(NOT_FOUND).build();
    }

    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentDays(Long idDonator) {
        List<AppointmentResponseDTO> appointments = new ArrayList<>();
        List<Appointment> appointmentDays = appointmentRepository.findByDonatorIdDonator(
            idDonator
        );
        logger.info("Appointments: {}", appointmentDays);

        if (!appointmentDays.isEmpty()) {
            appointmentDays.forEach(it -> appointments.add(
                AppointmentResponseDTO.builder()
                    .idAppointment(it.getIdAppointment())
                    .dhAppointment(it.getDhAppointment())
                    .accepted(it.isAccepted())
                    .idDonator(it.getDonator().getIdDonator())
                    .bloodType(it.getDonator().getBloodType())
                    .name(it.getDonator().getUser().getName())
                    .email(it.getDonator().getUser().getEmail())
                    .documentNumber(it.getDonator().getUser().getDocumentNumber())
                    .phone(it.getDonator().getUser().getPhone())
                    .idAddress(it.getDonator().getUser().getAddress().getIdAddress())
                    .address(it.getDonator().getUser().getAddress().getAddress())
                    .neighborhood(it.getDonator().getUser().getAddress().getNeighborhood())
                    .city(it.getDonator().getUser().getAddress().getCity())
                    .uf(it.getDonator().getUser().getAddress().getUf())
                    .cep(it.getDonator().getUser().getAddress().getCep())
                    .number(it.getDonator().getUser().getAddress().getNumber())
                    .idHospitalAppointment(it.getHospitalAppointment().getIdHospitalAppointment())
                    .idHospital(it.getHospitalAppointment().getHospital().getIdHospital())
                .build()
            ));
            return status(OK).body(appointments);
        }

        return status(NOT_FOUND).build();
    }

    public ResponseEntity<?> cancelAppointmentDay(Long idAppointment) {
        if (appointmentRepository.existsById(idAppointment)) {
            appointmentRepository.deleteById(idAppointment);
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

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
