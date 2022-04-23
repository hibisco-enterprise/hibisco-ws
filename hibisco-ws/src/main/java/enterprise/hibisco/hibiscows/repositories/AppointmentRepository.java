package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByFkDonatorAndFkHospital(Long fkDonator, Long fkHospital);

    @Query("update Appointment a set a.accepted = true where a.idAppointment = ?1")
    @Transactional
    @Modifying
    void acceptAppointmentDay(Long idAppointment);
}
