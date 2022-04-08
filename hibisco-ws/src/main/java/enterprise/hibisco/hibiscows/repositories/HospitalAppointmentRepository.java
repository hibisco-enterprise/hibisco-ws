package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HospitalAppointmentRepository extends JpaRepository<HospitalAppointment, Long> {

    boolean existsByFkHospital(Long fkHospital);

    @Query("select ha from HospitalAppointment ha where ha.fkHospital = ?1")
    List<HospitalAppointment> getAppointmentDaysByIdHospital(Long idHospital);

    @Query("select ha from HospitalAppointment ha where ha.dhAvaliable = ?1 and fkHospital = ?2")
    Optional<HospitalAppointment> getAvaliableDateFromIdHospital(LocalDateTime dhAvaliable, Long idHospital);

}
