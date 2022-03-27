package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.responses.DonatorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonatorService {

    @Autowired
    private DonatorRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity doRegister(DonatorResponseDTO donator) {
        if (repository.existsByCpf(donator.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido, tente novamente com um cpf diferente");
        }

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
            donator.getPassword(),
            donator.getPhone(),
            donator.getNameDonator(),
            donator.getCpf(),
            donator.getBloodType(),
            fkAddress
        );

        repository.save(newDonator);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity getDonators() {
        return ResponseEntity.status(donators.isEmpty() ? 204 : 200).body(donators);
    }

    public ResponseEntity doLogin(User donator) {
        for (User u: donators) {
            if (u.authenticate(donator)) {
                u.setAuthenticated(true);
                return ResponseEntity.status(200).build();
            }
        }

        return ResponseEntity.status(401).build();
    }

    public ResponseEntity doLogoff(String email) {
        for (User user : donators) {
            if (user.getEmail().equals(email)) {
                user.setAuthenticated(false);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.status(404).build();
    }

}
