package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.request.AvaliableDaysWrapperRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HospitalAppointmentRepository extends JpaRepository<HospitalAppointment, Long> {

    List<HospitalAppointment> findByHospitalIdHospital(Long idHospital);

    @Transactional
    @Modifying
    @Query("update Appointment a set a.accepted = true where a.idAppointment = ?1")
    void acceptAppointmentDay(Long idAppointment);

}
