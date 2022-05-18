package enterprise.hibisco.hibiscows.service;

import com.google.gson.Gson;
import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.request.HospitalRequestDTO;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@SuppressWarnings("unused")
public class HospitalService {

    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);
    private static final Gson gson = new Gson();

    @Autowired
    private HospitalRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressDataService addressDataService;


    public ResponseEntity<?> doRegister(HospitalRequestDTO hospital) {
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

            if (!hospital.getCnpjHospital().equals(findHospital.get().getCnpjHospital())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(hospital, newHospital);

            newHospital.setFkAddress(findHospital.get().getFkAddress());
            newHospital.setIdUser(idHospital);
            repository.save(newHospital);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> updatePassword(Long idDonator, String password) {
        Optional<Hospital> findHospital = repository.findById(idDonator);

        if (findHospital.isPresent()) {
            repository.updatePassword(idDonator, password);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteHospital(Long idUser) {
        Long idAddress;
        Optional<Long> findIdAddress = repository.findFkAddressByIdHospital(idUser);
        if (repository.existsById(idUser) && findIdAddress.isPresent()) {
            idAddress = findIdAddress.get();
            addressRepository.deleteById(idAddress);
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

    public ResponseEntity<?> doLogin(HospitalRequestDTO hospital) {
        Optional<Hospital> findHospital = repository.findByEmailAndPassword(
            hospital.getEmail(),
            hospital.recoverPassword()
        );
        if (findHospital.isPresent()) {
            repository.authenticateUser(findHospital.get().getIdUser());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> doLogoff(Long idUser) {
        repository.removeAuthenticationUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
