package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.AddressData;
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

    @Query("select d from Donator d inner join User u on u.idUser = d.fkUser where u.documentNumber = ?1")
    Optional<Donator> findByDocumentNumber(String documentNumber);

    @Query("select d from Donator d inner join User u on u.idUser = d.fkUser " + "where u.email = ?1 and u.password = ?2")
    Optional<Donator> findByEmailAndPassword(String email, String password);


//    @Query("select d.address from Donator d where d.idUser = ?1")
//    Optional<AddressData> findFkAddressByIdDonator(Long idUser);
//
//    @Transactional
//    @Modifying
//    @Query("update Donator d set d.password = ?2 where d.idUser = ?1")
//    void updatePassword(Long idUser, String password);
//
//    @Transactional
//    @Modifying
//    @Query("update Donator d set d.authenticated = 1 where d.idUser = ?1")
//    void authenticateUser(Long idUser);
//
//    @Transactional
//    @Modifying
//    @Query("update Donator d set d.authenticated = 0 where d.idUser = ?1")
//    void removeAuthenticationUser(Long idUser);
}