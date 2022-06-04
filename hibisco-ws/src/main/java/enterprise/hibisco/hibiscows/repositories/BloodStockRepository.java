package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.BloodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BloodStockRepository extends JpaRepository<BloodStock, Long> {

    @Transactional
    @Modifying
    @Query("update BloodStock b set b.percentage = ?2 where b.idBloodStock = ?1")
    int updateBloodStock(Long id, Double percentage);

    Optional<BloodStock> findByBloodTypeAndHospitalIdHospital(String BloodType, Long idHospital);
}
