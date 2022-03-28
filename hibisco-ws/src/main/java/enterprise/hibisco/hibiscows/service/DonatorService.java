package enterprise.hibisco.hibiscows.service;

import com.sun.xml.bind.v2.TODO;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.responses.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DonatorService {

    @Autowired
    private DonatorRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity doRegister(UserResponseDTO donator) {
        if (repository.existsByCpf(donator.getCpf().toString())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido, tente novamente com um cpf diferente");
        }

        try {
            Long fkAddress = addressRepository.saveAddressReturningId(
                    donator.getAddress(),
                    donator.getNeighborhood(),
                    donator.getCity(),
                    donator.getUf(),
                    donator.getCep(),
                    donator.getNumber()
            );

            Donator newDonator = new Donator(
                    donator.getEmail(),
                    donator.recoverPassword(),
                    donator.getPhone(),
                    donator.getNameDonator().toString(),
                    donator.getCpf().toString(),
                    donator.getBloodType().toString(),
                    fkAddress
            );

            repository.save(newDonator);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity getDonators() {
        if (repository.count() > 0) {
            var donators = repository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(donators);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity doLogin(UserResponseDTO donator) {
        //TODO: criar objeto doador e efetuar login chamando authenticate
        var login = repository.findLoginAndPassword(donator.getEmail(), donator.recoverPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
        //falta algumas coisas a implementar
    }

    public ResponseEntity doLogoff(String email) {
        //TODO: criar objeto doador e efetuar busca na base, caso exista, fazer logoff
        return ResponseEntity.status(404).build();
    }

}
