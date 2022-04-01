package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
