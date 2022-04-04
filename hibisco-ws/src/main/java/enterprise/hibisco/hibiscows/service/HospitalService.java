package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.responses.HospitalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity doRegister(HospitalResponseDTO hospital) {
        if (repository.existsByCnpj(hospital.getCnpjHospital())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "CPF inválido, tente novamente com um cpf diferente"
            );
        }

        try {
            AddressData fkAddress = addressRepository.save(
                new AddressData(
                    hospital.getAddress(),
                    hospital.getNeighborhood(),
                    hospital.getCity(),
                    hospital.getUf(),
                    hospital.getCep(),
                    hospital.getNumber()
                )
            );

            repository.save(
                new Hospital(
                    hospital.getEmail(),
                    hospital.recoverPassword(),
                    hospital.getPhone(),
                    hospital.getNameHospital(),
                    hospital.getCnpjHospital(),
                    fkAddress.getIdAddress()
                )
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    public ResponseEntity getHospitals() {
        if (repository.count() > 0) {
            var hospitals = repository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(hospitals);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity doLogin(HospitalResponseDTO hospital) {
        int login = repository.findLoginAndPassword(hospital.getEmail(), hospital.recoverPassword());
        if (login == 1) {
            Long idUser = repository.getIdUser(hospital.getEmail(), hospital.recoverPassword());
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
