package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Donator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface DonatorRepository extends JpaRepository<Donator, Long> {

    boolean existsByCpf(String cpf);

    Optional<Donator> findByCpf(String cpf);

    Optional<Donator> findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query("update Donator d set d.authenticated = 1 where d.idUser = ?1")
    void authenticateUser(Long idUser);

    @Transactional
    @Modifying
    @Query("update Donator d set d.authenticated = 0 where d.idUser = ?1")
    void removeAuthenticationUser(Long idUser);
}