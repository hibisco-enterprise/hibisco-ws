package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.Donator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface DonatorRepository extends JpaRepository<Donator, Long> {

    boolean existsByCpf(String cpf);

    @Query("select count(s.id_user) from tb_donator s where s.email = ?1 and s.password = ?2")
    int findLoginAndPassword(String email, String password);

    @Query("select s.id_user from tb_donator s where s.email = ?1 and s.password = ?2")
    Long getIdUser(String email, String password);

    @Query("update tb_donator d set d.authenticated = 1 where id_user = ?1")
    void authenticateUser(Long idUser);

    @Query("update tb_donator d set d.authenticated = 0 where id_user = ?1")
    void removeAuthenticationUser(Long idUser);
}