package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HospitalAppointmentRepository extends JpaRepository<HospitalAppointment, Long> {

    boolean existsByFkHospital(Long fkHospital);

    List<HospitalAppointment> findByFkHospital(Long idHospital);

//    Optional<HospitalAppointment> findByDhAvaliableAndFkHospital(LocalDateTime dhAvaliable, Long idHospital);

}
