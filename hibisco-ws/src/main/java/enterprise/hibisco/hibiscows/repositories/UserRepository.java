package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByDocumentNumber(String documentNumber);

    Optional<AddressData> findAddressByIdUser(Long idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.idUser = ?1")
    void updatePassword(Long idUser, String password);

    @Transactional
    @Modifying
    @Query("update User u set u.authenticated = 1 where u.idUser = ?1")
    void authenticateUser(Long idUser);

    @Transactional
    @Modifying
    @Query("update User u set u.authenticated = 0 where u.idUser = ?1")
    void removeAuthenticationUser(Long idUser);
}
