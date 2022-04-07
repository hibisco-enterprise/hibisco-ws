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
import java.util.List;
import java.util.Optional;

@Service
public class DonatorService {

    @Autowired
    private DonatorRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity doRegister(DonatorResponseDTO donator) {
        if (repository.existsByCpf(donator.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "CPF inválido, tente novamente com um cpf diferente"
            );
        }

        try {
            AddressData fkAddress = addressRepository.save(
                new AddressData(
                    donator.getAddress(),
                    donator.getNeighborhood(),
                    donator.getCity(),
                    donator.getUf(),
                    donator.getCep(),
                    donator.getNumber()
                )
            );

            repository.save(
                new Donator(
                    donator.getEmail(),
                    donator.recoverPassword(),
                    donator.getPhone(),
                    donator.getNameDonator(),
                    donator.getCpf(),
                    donator.getBloodType(),
                    fkAddress.getIdAddress()
                )
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    public ResponseEntity<List<Donator>> getDonators() {
        if (repository.count() > 0) {
            var donators = repository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(donators);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Optional<Donator>> getDonatorById(Long idUser) {
        var user = repository.findById(idUser);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity updateDonator(Donator donator) {
        repository.updateDonator(
            donator.getIdUser(),
            donator.getNameDonator(),
            donator.getDocument(),
            donator.getBloodType(),
            donator.getEmail(),
            donator.recoverPassword(),
            donator.getPhone()
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity doLogin(DonatorResponseDTO donator) {
        int login = repository.findLoginAndPassword(donator.getEmail(), donator.recoverPassword());
        if (login == 1) {
            Long idUser = repository.getIdUser(donator.getEmail(), donator.recoverPassword());
            repository.authenticateUser(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity doLogoff(Long idUser) {
        repository.removeAuthenticationUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
