package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unused")
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    boolean existsByCnpjHospital(String cnpjHospital);

    @Query("select count(h.id_user) from Hospital h where h.email = ?1 and h.password = ?2")
    int findLoginAndPassword(String email, String password);

    @Query("select h.id_user from Hospital h where h.email = ?1 and h.password = ?2")
    Long getIdUser(String email, String password);

    @Transactional
    @Modifying
    @Query("update Hospital h set h.authenticated = 1 where h.id_user = ?1")
    void authenticateUser(Long idUser);

    @Transactional
    @Modifying
    @Query("update Hospital h set h.authenticated = 0 where h.id_user = ?1")
    void removeAuthenticationUser(Long idUser);
}
