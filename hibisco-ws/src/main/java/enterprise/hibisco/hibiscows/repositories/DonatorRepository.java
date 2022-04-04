package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Donator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unused")
public interface DonatorRepository extends JpaRepository<Donator, Long> {

    boolean existsByCpf(String cpf);

    @Query("select count(d.id_user) from Donator d where d.email = ?1 and d.password = ?2")
    int findLoginAndPassword(String email, String password);

    @Query("select d.id_user from Donator d where d.email = ?1 and d.password = ?2")
    Long getIdUser(String email, String password);

    @Transactional
    @Modifying
    @Query("update Donator d set d.authenticated = 1 where d.id_user = ?1")
    void authenticateUser(Long idUser);

    @Transactional
    @Modifying
    @Query("update Donator d set d.authenticated = 0 where d.id_user = ?1")
    void removeAuthenticationUser(Long idUser);
}