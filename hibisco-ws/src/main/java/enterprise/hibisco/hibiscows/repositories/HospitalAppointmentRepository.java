package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HospitalAppointmentRepository extends JpaRepository<HospitalAppointment, Long> {

    boolean existsByFkHospital(Long fkHospital);

    List<HospitalAppointment> findByFkHospital(Long idHospital);

    @Transactional
    @Modifying
    @Query("update Appointment a set a.accepted = true where a.idAppointment = ?1")
    void acceptAppointmentDay(Long idAppointment);

}
