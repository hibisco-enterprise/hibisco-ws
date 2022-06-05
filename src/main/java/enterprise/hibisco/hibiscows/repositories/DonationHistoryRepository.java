package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.DonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {

}
