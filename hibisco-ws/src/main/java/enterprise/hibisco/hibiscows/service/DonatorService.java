package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.responses.DonatorResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class DonatorService {

    @Autowired
    private DonatorRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<?> doRegister(DonatorResponseDTO donator) {
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
        Optional<Donator> user = repository.findById(idUser);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Optional<Donator>> getDonatorByCpf(String cpf) {
        Optional<Donator> user = repository.findByCpf(cpf);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> updateDonator(Long idUser, Donator donator) {
        Optional<Donator> findDonator = repository.findById(idUser);
        ModelMapper mapper = new ModelMapper();
        Donator newDonator = new Donator();

        if (findDonator.isPresent()) {
            BeanUtils.copyProperties(findDonator, newDonator);

            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(donator, newDonator);

            newDonator.setIdUser(idUser);
            repository.save(newDonator);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteDonator(Long idUser) {
        if (repository.existsById(idUser)) {
            repository.deleteById(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> doLogin(DonatorResponseDTO donator) {
        int login = repository.findLoginAndPassword(donator.getEmail(), donator.recoverPassword());
        if (login == 1) {
            Long idUser = repository.getIdUser(donator.getEmail(), donator.recoverPassword());
            repository.authenticateUser(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> doLogoff(Long idUser) {
        repository.removeAuthenticationUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
