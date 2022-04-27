package enterprise.hibisco.hibiscows.service;

import com.google.gson.Gson;
import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.manager.CsvType;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.request.CsvRequestDTO;
import enterprise.hibisco.hibiscows.request.DonatorRequestDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unused")
public class DonatorService {

    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);
    private static final Gson gson = new Gson();
    @Autowired
    private DonatorRepository repository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressDataService addressDataService;

    public ResponseEntity<?> doRegister(DonatorRequestDTO donator) {
        if (repository.existsByCpf(donator.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "CPF inválido, tente novamente com um cpf diferente"
            );
        }

        try {
            AddressData fkAddress = addressRepository.save(
                new AddressData(
                    donator.getAddress(),
                    donator.getCep(),
                    donator.getCity(),
                    donator.getNeighborhood(),
                    donator.getNumber(),
                    donator.getUf()
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
            logger.error("Erro de criação de usuário: \n\t{} \nErro: \n\t{}",
                gson.toJson(donator), gson.toJson(e.getMessage())
            );
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

            if (!donator.getCpf().equals(findDonator.get().getCpf())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(donator, newDonator);

            newDonator.setFkAddress(findDonator.get().getFkAddress());
            newDonator.setIdUser(idUser);

            repository.save(newDonator);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> updatePassword(Long idDonator, String password) {
        Optional<Donator> findDonator = repository.findById(idDonator);

        if (findDonator.isPresent()) {
            repository.updatePassword(idDonator, password);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteDonator(Long idUser) {
        Long idAddress;
        Optional<Long> findIdAddress = repository.findFkAddressByIdDonator(idUser);
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

    public ResponseEntity<?> doLogin(DonatorRequestDTO donator) {
        Optional<Donator>findDonator = repository.findByEmailAndPassword(
            donator.getEmail(),
            donator.recoverPassword()
        );
        if (findDonator.isPresent()) {
            repository.authenticateUser(findDonator.get().getIdUser());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> doLogoff(Long idUser) {
        repository.removeAuthenticationUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> getReport(Long id) {
        Optional<Hospital> h1 = hospitalRepository.findById(id);
        Optional<AddressData> data = addressRepository.findById(h1.get().getFkAddress());
        if (data.isPresent()) {

            CsvRequestDTO csv = new CsvRequestDTO(
                CsvType.Hospital,
                h1.get().getNameHospital(),
                h1.get().getEmail(),
                h1.get().getPhone(),
                data.get().getAddress(),
                data.get().getNeighborhood(),
                data.get().getCity(),
                data.get().getUf(),
                data.get().getCep(),
                data.get().getNumber().toString()
            );

            String relatorio = String.join(", ",
                csv.getType().name(),
                csv.getName(),
                csv.getEmail(),
                csv.getPhoneNumber(),
                csv.getAddress(),
                csv.getNeighborhood(),
                csv.getCity(),
                csv.getUf(),
                csv.getCep(),
                csv.getNumber()
            );

            relatorio += "\r\n";

            return ResponseEntity
                    .status(200)
                    .header("content-type", "text/csv")
                    .header("content-disposition", "filename=\"hospital.csv\"")
                    .body(relatorio);
        }
            return ResponseEntity.status(404).build();
    }
}
