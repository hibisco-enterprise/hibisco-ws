package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Donator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface DonatorRepository extends JpaRepository<Donator, Long> {

    //@Query("select d from Donator d inner join User u on u.idUser = d.user where u.documentNumber = ?1")
    Optional<Donator> findByUserDocumentNumberIgnoreCase(String documentNumber);

    @Query("select d from Donator d inner join User u on u.idUser = d.user " + "where u.email = ?1 and u.password = ?2")
    Optional<Donator> findByEmailAndPassword(String email, String password);

}