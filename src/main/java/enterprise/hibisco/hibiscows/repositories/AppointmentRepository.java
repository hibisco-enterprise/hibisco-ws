package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDonatorIdDonatorOrderByDhAppointmentDesc(Long idDonator);

    List<Appointment> findByDonatorIdDonatorAndAcceptedTrueOrderByDhAppointmentDesc(Long idDonator);

    @Query("update Appointment a set a.accepted = true where a.idAppointment = ?1")
    @Transactional
    @Modifying
    void acceptAppointmentDay(Long idAppointment);

    List<Appointment> findByHospitalIdHospitalOrderByDhAppointment(Long idHospital);

    List<Appointment> findByHospitalIdHospitalAndAcceptedFalseOrderByDhAppointment(Long idHospital);
    List<Appointment> findByHospitalIdHospitalAndAcceptedTrueOrderByDhAppointment(Long idHospital);
}
