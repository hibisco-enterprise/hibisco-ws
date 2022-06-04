package enterprise.hibisco.hibiscows.controller;

import com.google.gson.Gson;
import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.BloodStock;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.manager.FileHandler;
import enterprise.hibisco.hibiscows.manager.PilhaObj;
import enterprise.hibisco.hibiscows.repositories.*;
import enterprise.hibisco.hibiscows.request.*;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.response.AvaliableDaysResponseDTO;
import enterprise.hibisco.hibiscows.rest.mapbox.LatLongDTO;
import enterprise.hibisco.hibiscows.service.AddressDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.status;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hospitals")
@SuppressWarnings("unused")
public class HospitalController {

    private static final Logger logger = LoggerFactory.getLogger(HospitalController.class);

    private static final Gson gson = new Gson();

    private PilhaObj<HospitalAppointment> appointmentsStack = new PilhaObj<>(5);


    @Autowired
    private AddressDataService addressDataService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HospitalAppointmentRepository hospitalAppointmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BloodStockRepository bloodStockRepository;


    @GetMapping
    public ResponseEntity<List<Hospital>> getHospitals() {
        if (hospitalRepository.count() > 0) {
            List<Hospital> hospitals = hospitalRepository.findAll();
            return status(OK).body(hospitals);
        }
        return status(NO_CONTENT).build();
    }

    @GetMapping("/{idHospital}")
    public ResponseEntity<Optional<Hospital>> getHospitalById(@PathVariable Long idHospital) {
        Optional<Hospital> hospital = hospitalRepository.findById(idHospital);
        if (hospital.isPresent()) {
            return status(OK).body(hospital);
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/{cnpjHospital}")
    public ResponseEntity<Optional<Hospital>> getHospitalByCnpj(@PathVariable String cnpjHospital) {
        Optional<Hospital> hospital = hospitalRepository.findByUserDocumentNumberIgnoreCase(cnpjHospital);
        if (hospital.isPresent()) {
            return status(OK).body(hospital);
        }
        return status(NOT_FOUND).build();
    }

    @PutMapping("/{idHospital}")
    public ResponseEntity<Void> updateHospital(@PathVariable Long idHospital,
                                               @RequestBody @Valid Hospital hospital) {
        Optional<Hospital> findHospital = hospitalRepository.findById(idHospital);
        if (findHospital.isPresent()) {

            if (!hospital.getUser().getDocumentNumber().equals(
                    findHospital.get().getUser().getDocumentNumber())
            ) {
                return status(NOT_ACCEPTABLE).build();
            }

            hospital.getUser().getAddress().setIdAddress(
                findHospital.get().getUser().getAddress().getIdAddress()
            );
            hospital.getUser().setIdUser(findHospital.get().getUser().getIdUser());
            hospital.setIdHospital(idHospital);
            logger.info("atualizando Hospital: {}", gson.toJson(hospital));

            hospitalRepository.save(hospital);

            return status(OK).build();
        }

        return status(NOT_FOUND).build();
    }

    @PatchMapping("password/{idHospital}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long idHospital,
                                            @RequestBody @Valid PasswordRequestDTO password) {
        Optional<Hospital> findHospital = hospitalRepository.findById(idHospital);

        if (findHospital.isPresent()) {
            userRepository.updatePassword(
                findHospital.get().getUser().getIdUser(),
                password.getPassword()
            );
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    @DeleteMapping("/{idHospital}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long idHospital) {
        if (hospitalRepository.existsById(idHospital)) {
            hospitalRepository.deleteById(idHospital);
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/address/{idAddress}")
    public ResponseEntity<Optional<AddressData>> getAddressById(@PathVariable Long idAddress) {
        AddressResponseDTO address = addressDataService.getAddressById(idAddress);
        if (address.getAddressData().isPresent()) {
            return status(address.getStatusCode()).body(address.getAddressData());
        }
        return status(address.getStatusCode()).build();
    }

    @PutMapping("/address/{idAddress}")
    public ResponseEntity<Optional<AddressData>> updateAddress(@PathVariable Long idAddress,
                                                               @RequestBody @Valid AddressData newAddress) {
        AddressResponseDTO address = addressDataService.updateAddress(idAddress, newAddress);
        if (address.getAddressData().isPresent()) {
            return status(address.getStatusCode()).body(address.getAddressData());
        }
        return status(address.getStatusCode()).build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody @Valid Hospital hospital) {
        if (userRepository.existsByDocumentNumber(hospital.getUser().getDocumentNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "CNPJ inválido, tente novamente com um cnpj diferente"
            );
        }

        ResponseEntity<LatLongDTO> coordinates = addressDataService.getGeocoordinates(
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
            hospitalRepository.save(
                new Hospital(
                    hospital.getUser()
                )
            );

            return status(CREATED).build();

        }catch (Exception e) {
            logger.error("Erro de criação de usuário: \n\t{} \nErro: \n\t{}",
                gson.toJson(hospital), gson.toJson(e.getMessage())
            );
            return status(BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Hospital> doLogin(@RequestBody @Valid HospitalLoginRequestDTO hospital) {
        Optional<Hospital> findHospital = hospitalRepository.findByEmailAndPassword(
            hospital.getEmail(),
            hospital.getPassword()
        );
        if (findHospital.isPresent()) {
            userRepository.authenticateUser(findHospital.get().getUser().getIdUser());
            findHospital.get().getUser().setAuthenticated(true);
            return status(OK).body(findHospital.get());
        }
        return status(NOT_FOUND).build();
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity<?> doLogoff(@PathVariable Long idUser) {
        if(userRepository.existsById(idUser)) {
            userRepository.removeAuthenticationUser(idUser);
            return status(OK).build();
        }
        return status(BAD_REQUEST).build();
    }

    @GetMapping("/appointment/{idHospital}")
    public ResponseEntity<List<AvaliableDaysResponseDTO>> getAvaliableDays(@PathVariable Long idHospital) {
        List<HospitalAppointment> appointments = hospitalAppointmentRepository.findByHospitalIdHospital(
            idHospital
        );

        List<AvaliableDaysResponseDTO> avaiableDays = new ArrayList<>();

        appointments.forEach(it -> avaiableDays.add(
            AvaliableDaysResponseDTO.builder()
                .idHospitalAppointment(it.getIdHospitalAppointment())
                .avaliableDay(it.getDhAvaliable())
                .idHospital(it.getHospital().getIdHospital())
                .build()
        ));

        if (appointments.isEmpty()) {
            return status(NO_CONTENT).build();
        }
        return status(OK).body(avaiableDays);
    }

    @PostMapping("/appointment/{idHospital}")
    public ResponseEntity<?> setAvaliableDays(@PathVariable Long idHospital,
                                                 @RequestBody @Valid AvaliableDaysWrapperRequestDTO avaliableDays) {
        Optional<Hospital> hospital = hospitalRepository.findById(idHospital);

        if (hospital.isPresent()) {
            avaliableDays.getAvaliableDays().forEach(day ->
                hospitalAppointmentRepository.save(
                    new HospitalAppointment(
                        day,
                        hospital.get()
                    )
                )
            );
            return status(CREATED).build();
        }

        return status(NOT_FOUND).build();
    }

    @DeleteMapping("/appointment/{idHospitalAppointment}")
    public ResponseEntity<Void> deleteAvaliableDay(@PathVariable Long idHospitalAppointment) {
        Optional<HospitalAppointment> appointment = hospitalAppointmentRepository.findById(
            idHospitalAppointment
        );
        if (appointment.isPresent()) {
            appointmentsStack.push(hospitalAppointmentRepository.getById(idHospitalAppointment));
            hospitalAppointmentRepository.deleteById(appointment.get().getIdHospitalAppointment());
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    @PostMapping("/appointment/accept/{idAppointment}")
    public ResponseEntity<Void> acceptAvailableDay(@PathVariable Long idAppointment) {
        if (hospitalAppointmentRepository.existsById(idAppointment)) {
            hospitalAppointmentRepository.acceptAppointmentDay(idAppointment);
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    @PostMapping("/appointment/reverse")
    public ResponseEntity<Void> reverseHospitalAppointmentDelete() {
        if (this.appointmentsStack.isEmpty()) {
            return status(204).build();
        } else {
            HospitalAppointment recover = appointmentsStack.pop();
            AvaliableDaysWrapperRequestDTO recovered =  new AvaliableDaysWrapperRequestDTO();
            recovered.setAvaliableDays(new ArrayList<>(recovered.getAvaliableDays()));
            setAvaliableDays(recover.getHospital().getIdHospital(), recovered);
            return status(201).build();
        }
    }

    @PostMapping(value = "/importacao-txt")
    public ResponseEntity<Void> importTxt(@RequestParam("file") MultipartFile file) {
        BufferedReader entrada;
        BloodRegisterRequestDTO bloodList = FileHandler.leArquivoTxt(file);

        return ResponseEntity.status(200).build();
    }

    @PostMapping("/blood/register")
    public ResponseEntity<Void> registerBloodStock(@RequestBody @Valid BloodRegisterRequestDTO bloodStock) {
        Optional<Hospital> hospital = hospitalRepository.findByUserDocumentNumberIgnoreCase(bloodStock.getDocumentNumber());

        if (hospital.isPresent()) {
            for (BloodTypeWrapperDTO blood: bloodStock.getBloodStock()) {
                BloodStock newBlood = new BloodStock(blood.getBloodType(), blood.getPercentage(), hospital.get());
                bloodStockRepository.save(newBlood);
            }

            return status(201).build();
        }
        return status(404).build();
    }

    @PutMapping("/blood/{idHospital}")
    public ResponseEntity<Integer> updateBloodStock(@PathVariable Long idHospital,
                                                    @RequestBody @Valid List<BloodTypeWrapperDTO> bloodStock) {
        Optional<Hospital> hospital = hospitalRepository.findById(idHospital);
        if (hospital.isEmpty()) { return status(404).build(); }

        Integer updated = 0;
        for (BloodTypeWrapperDTO b: bloodStock) {
            Optional<BloodStock> current =  bloodStockRepository.findByBloodTypeAndHospitalIdHospital(b.getBloodType(), idHospital);
            if (current.isPresent()) {
                bloodStockRepository.updateBloodStock(current.get().getIdBloodStock(), b.getPercentage());
                updated++;
            }
        }

        return status(200).body(updated);
    }

    @GetMapping("/blood/{idHospital}")
    public ResponseEntity<List<BloodTypeWrapperDTO>> getBloodStock(@PathVariable Long idHospital) {
        List<BloodStock> bloodStocks = bloodStockRepository.findByHospitalIdHospital(idHospital);
        if (bloodStocks.isEmpty()) { return status(204).build(); }

        List<BloodTypeWrapperDTO> bloodStockList = new ArrayList<>();
        for(BloodStock b: bloodStocks) {
            bloodStockList.add(new BloodTypeWrapperDTO(b.getBloodType(), b.getPercentage()));
        }

        return status(200).body(bloodStockList);
    }

}
