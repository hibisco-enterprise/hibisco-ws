package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HospitalAppointmentRepository extends JpaRepository<HospitalAppointment, Long> {

    boolean existsByFkHospital(Long fkHospital);

    @Query("select ha from HospitalAppointment ha where ha.fkHospital = ?1")
    List<HospitalAppointment> getAppointmentDaysByIdHospital(Long idHospital);

}
