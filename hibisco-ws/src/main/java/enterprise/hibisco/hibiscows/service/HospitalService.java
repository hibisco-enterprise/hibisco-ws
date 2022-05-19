package enterprise.hibisco.hibiscows.service;

import com.google.gson.Gson;
import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.repositories.UserRepository;
import enterprise.hibisco.hibiscows.request.HospitalLoginRequestDTO;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.rest.positionstack.PositionStackResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@SuppressWarnings("unused")
public class HospitalService {

    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);
    private static final Gson gson = new Gson();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HospitalRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressDataService addressDataService;


    public ResponseEntity<?> doRegister(Hospital hospital) {
        if (userRepository.existsByDocumentNumber(hospital.getUser().getDocumentNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "CNPJ inválido, tente novamente com um cnpj diferente"
            );
        }

        ResponseEntity<PositionStackResponse> coordinates = addressDataService.getGeocoordinates(
            hospital.getUser().getAddress()
        );

        hospital.getUser().getAddress().setLatitude(
            Objects.requireNonNull(coordinates.getBody()).getLatitude()
        );

        hospital.getUser().getAddress().setLongitude(
            Objects.requireNonNull(coordinates.getBody()).getLongitude()
        );



        logger.info("Endereço: {}", gson.toJson(hospital.getUser().getAddress()));

        try {
            repository.save(
                new Hospital(
                    hospital.getUser()
                )
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();

        }catch (Exception e) {
            logger.error("Erro de criação de usuário: \n\t{} \nErro: \n\t{}",
                gson.toJson(hospital), gson.toJson(e.getMessage())
            );
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
        Optional<Hospital> hospital = repository.findByDocumentNumber(cnpjHospital);
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

            if (
                !hospital.getUser().getDocumentNumber().equals(
                    findHospital.get().getUser().getDocumentNumber())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(hospital, newHospital);

            newHospital.setIdHospital(idHospital);
            repository.save(newHospital);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> updatePassword(Long idHospital, String password) {
        Optional<Hospital> findHospital = repository.findById(idHospital);

        if (findHospital.isPresent()) {
            userRepository.updatePassword(
                    findHospital.get().getUser().getIdUser(),
                    password
            );
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

    public ResponseEntity<Optional<AddressData>> getAddressById(Long idAddress) {
        AddressResponseDTO address = addressDataService.getAddressById(idAddress);
        if (address.getAddressData().isPresent()) {
            return ResponseEntity.status(address.getStatusCode()).body(address.getAddressData());
        }
        return ResponseEntity.status(address.getStatusCode()).build();
    }

    public ResponseEntity<Optional<AddressData>> updateAddressById(Long idAddress, AddressData newAddress) {
        AddressResponseDTO address = addressDataService.updateAddress(idAddress, newAddress);
        if (address.getAddressData().isPresent()) {
            return ResponseEntity.status(address.getStatusCode()).body(address.getAddressData());
        }
        return ResponseEntity.status(address.getStatusCode()).build();
    }

    public ResponseEntity<?> doLogin(HospitalLoginRequestDTO hospital) {
        Optional<Hospital> findHospital = repository.findByEmailAndPassword(
            hospital.getEmail(),
            hospital.getPassword()
        );
        if (findHospital.isPresent()) {
            userRepository.authenticateUser(findHospital.get().getUser().getIdUser());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> doLogoff(Long idUser) {
        userRepository.removeAuthenticationUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
