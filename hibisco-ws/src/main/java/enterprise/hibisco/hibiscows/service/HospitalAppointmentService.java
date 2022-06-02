package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.manager.PilhaObj;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.request.AvaliableDaysWrapperRequestDTO;
import enterprise.hibisco.hibiscows.response.AvaliableDaysResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalAppointmentService {
    private PilhaObj<HospitalAppointment> appointmentsStack = new PilhaObj<>(5);

    @Autowired
    private HospitalAppointmentRepository repository;

    @Autowired
    private HospitalRepository hospitalRepository;

    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);

    public ResponseEntity<List<AvaliableDaysResponseDTO>> getAvaliableDays(Long idHospital) {
        List<HospitalAppointment> appointments = repository.findByHospitalIdHospital(idHospital);

        List<AvaliableDaysResponseDTO> avaiableDays = new ArrayList<>();

        appointments.forEach(it -> {
            avaiableDays.add(
                AvaliableDaysResponseDTO.builder()
                    .idHospitalAppointment(it.getIdHospitalAppointment())
                    .avaliableDay(it.getDhAvaliable())
                    .idHospital(it.getHospital().getIdHospital())
                .build()
            );
        });

        if (appointments.isEmpty()) {
            return status(NO_CONTENT).build();
        }
        return status(OK).body(avaiableDays);
    }

    public ResponseEntity<Void> setAvaliableDays(
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

    public ResponseEntity<Void> deleteAvaliableDay(
        Long idHospitalAppointment
    ) {
        Optional<HospitalAppointment> appointment = repository.findById(idHospitalAppointment);
        if (appointment.isPresent()) {
            appointmentsStack.push(repository.getById(idHospitalAppointment));
            repository.deleteById(appointment.get().getIdHospitalAppointment());
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    public ResponseEntity<Void> acceptAppointmentDay(Long idAppointment) {
        if (repository.existsById(idAppointment)) {
            repository.acceptAppointmentDay(idAppointment);
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }


}
