package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface HospitalRepository extends JpaRepository<Hospital, Long> {


    // @Query("select address")
    //boolean existsByCnpjHospital(String cnpjHospital);

    @Query("select h from Hospital h inner join User u on u.idUser = h.user where u.documentNumber = ?1")
    Optional<Hospital> findByDocumentNumber(String documentNumber);

    @Query("select h from Hospital h inner join User u on u.idUser = h.user " + "where u.email = ?1 and u.password = ?2")
    Optional<Hospital> findByEmailAndPassword(String email, String password);

    @Query("select u.name from Hospital h inner join User u on u.idUser = h.user where h.idHospital = ?1")
    String findNameHospitalByIdUser(Long idHospital);

//    @Transactional
//    @Modifying
//    @Query("update Hospital h set h.password = ?2 where h.idUser = ?1")
//    void updatePassword(Long idUser, String password);
//
//    @Transactional
//    @Modifying
//    @Query("update Hospital h set h.authenticated = 1 where h.idUser = ?1")
//    void authenticateUser(Long idUser);
//
//    @Transactional
//    @Modifying
//    @Query("update Hospital h set h.authenticated = 0 where h.idUser = ?1")
//    void removeAuthenticationUser(Long idUser);
}
