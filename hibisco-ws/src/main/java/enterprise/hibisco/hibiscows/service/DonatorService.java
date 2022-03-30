package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.responses.DonatorResponseDTO;
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

    public ResponseEntity doRegister(DonatorResponseDTO donator) {
        if (repository.existsByCpf(donator.getCpf().toString())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido, tente novamente com um cpf diferente");
        }

        AddressData address = new AddressData(
            donator.getAddress(),
            donator.getNeighborhood(),
            donator.getCity(),
            donator.getUf(),
            donator.getCep(),
            donator.getNumber()
        );

        try {
            var fkAddress = addressRepository.save(address);

            Donator newDonator = new Donator(
                    donator.getEmail(),
                    donator.recoverPassword(),
                    donator.getPhone(),
                    donator.getNameDonator(),
                    donator.getCpf(),
                    donator.getBloodType(),
                    fkAddress.getIdAddress()
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

    public ResponseEntity doLogin(DonatorResponseDTO donator) {
        //TODO: criar objeto doador e efetuar login chamando authenticate
        var login = repository.findLoginAndPassword(donator.getEmail(), donator.recoverPassword());
        if (login == 1) {
            var idUser = repository.getIdUser(donator.getEmail(), donator.recoverPassword());
            repository.authenticateUser(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity doLogoff(Long idUser) {
        return ResponseEntity.status(404).build();
    }

}
