package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.AddressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<AddressData, Long> {
}
