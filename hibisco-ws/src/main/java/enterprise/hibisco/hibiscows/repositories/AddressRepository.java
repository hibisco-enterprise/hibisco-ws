package enterprise.hibisco.hibiscows.repositories;

import enterprise.hibisco.hibiscows.entities.AddressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<AddressData, Long> {

    @Query("insert into AddressData (address, neighborhood, city, uf, cep, number) values (?1, ?2, ?3, ?4, ?5, ?6) returning id_address")
    Long saveAddressReturningId(String address, String neighborhood, String city, String uf, String cep, Integer number);

}
