package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface HospitalRepository extends JpaRepository<Hospital, Long> {


    @Query("select address")
    boolean existsByCnpjHospital(String cnpjHospital);

    Optional<Hospital> findByCnpjHospital(String cnpjHospital);

    Optional<Hospital> findByEmailAndPassword(String email, String password);

    @Query("select h.fkAddress from Hospital h where h.idUser = ?1")
    Optional<Long> findFkAddressByIdHospital(Long idUser);

    @Transactional
    @Modifying
    @Query("update Hospital h set h.password = ?2 where h.idUser = ?1")
    void updatePassword(Long idUser, String password);

    @Transactional
    @Modifying
    @Query("update Hospital h set h.authenticated = 1 where h.idUser = ?1")
    void authenticateUser(Long idUser);

    @Transactional
    @Modifying
    @Query("update Hospital h set h.authenticated = 0 where h.idUser = ?1")
    void removeAuthenticationUser(Long idUser);
}
