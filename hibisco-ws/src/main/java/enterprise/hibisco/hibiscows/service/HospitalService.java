package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalAppointmentRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.responses.HospitalResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class HospitalService {

    @Autowired
    private HospitalRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<?> doRegister(HospitalResponseDTO hospital) {
        if (repository.existsByCnpjHospital(hospital.getCnpjHospital())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "CNPJ inválido, tente novamente com um cnpj diferente"
            );
        }

        try {
            AddressData fkAddress = addressRepository.save(
                new AddressData(
                    hospital.getAddress(),
                    hospital.getCep(),
                    hospital.getCity(),
                    hospital.getNeighborhood(),
                    hospital.getNumber(),
                    hospital.getUf()
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

    public ResponseEntity<List<Hospital>> getHospitals() {
        if (repository.count() > 0) {
            List<Hospital> hospitals = repository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(hospitals);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<Optional<Hospital>> getHospitalById(Long idUser) {
        Optional<Hospital> hospital = repository.findById(idUser);
        if (hospital.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(hospital);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Optional<Hospital>> getDonatorByCnpj(String cnpjHospital) {
        Optional<Hospital> hospital = repository.findByCnpjHospital(cnpjHospital);
        if (hospital.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(hospital);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> updateHospital(Long idHospital, Hospital hospital) {
        Optional<Hospital> findHospital = repository.findById(idHospital);
        ModelMapper mapper = new ModelMapper();
        Hospital newHospital = new Hospital();
        if (findHospital.isPresent()) {

            BeanUtils.copyProperties(findHospital, newHospital);

            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(hospital, newHospital);

            newHospital.setIdUser(idHospital);
            repository.save(newHospital);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteHospital(Long idUser) {
        if (repository.existsById(idUser)) {
            repository.deleteById(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> doLogin(HospitalResponseDTO hospital) {
        int login = repository.findLoginAndPassword(hospital.getEmail(), hospital.recoverPassword());
        if (login == 1) {
            Long idUser = repository.getIdUser(hospital.getEmail(), hospital.recoverPassword());
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
