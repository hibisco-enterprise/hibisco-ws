package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByFkDonatorAndFkHospital(Long fkDonator, Long fkHospital);

}
