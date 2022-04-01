package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalAppointmentRepository extends JpaRepository<HospitalAppointment, Long> {
}
