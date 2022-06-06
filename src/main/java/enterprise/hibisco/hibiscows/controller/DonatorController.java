package enterprise.hibisco.hibiscows.controller;

import com.google.gson.Gson;
import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.manager.FileHandler;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.repositories.*;
import enterprise.hibisco.hibiscows.request.AppointmentRequestDTO;
import enterprise.hibisco.hibiscows.request.DonatorLoginRequestDTO;
import enterprise.hibisco.hibiscows.request.PasswordRequestDTO;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.response.AppointmentResponseDTO;
import enterprise.hibisco.hibiscows.rest.mapbox.LatLongDTO;
import enterprise.hibisco.hibiscows.service.AddressDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/donators")
@SuppressWarnings("unused")
public class DonatorController {
    @Autowired
    private HospitalRepository hospitalRepository;

    private static final Logger logger = LoggerFactory.getLogger(DonatorController.class);
    private static final Gson gson = new Gson();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonatorRepository donatorRepository;

    @Autowired
    private AddressDataService addressDataService;

    @Autowired
    private AppointmentRepository appointmentRepository;


    @GetMapping
    public ResponseEntity<List<Donator>> getDonators() {
        if (donatorRepository.count() > 0) {
            var donators = donatorRepository.findAll();
            return status(OK).body(donators);
        }
        return status(NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Donator>> getDonatorById(@PathVariable Long idDonator) {
        Optional<Donator> user = donatorRepository.findById(idDonator);
        if (user.isPresent()) {
            return status(OK).body(user);
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Optional<Donator>> getDonatorByCpf(@PathVariable String cpf) {
        Optional<Donator> user = donatorRepository.findByUserDocumentNumberIgnoreCase(cpf);
        if (user.isPresent()) {
            return status(OK).body(user);
        }
        return status(NOT_FOUND).build();
    }

    @PutMapping("/{idDonator}")
    public ResponseEntity<?> updateDonator(@PathVariable Long idDonator,
                                           @RequestBody @Valid Donator donator) {
        Optional<Donator> findDonator = donatorRepository.findById(idDonator);
        if (findDonator.isPresent()) {

            if (!donator.getUser().getDocumentNumber().equals(
                    findDonator.get().getUser().getDocumentNumber())
            ) {
                return status(NOT_ACCEPTABLE).build();
            }

            donator.getUser().getAddress().setIdAddress(
                findDonator.get().getUser().getAddress().getIdAddress()
            );
            donator.getUser().setIdUser(findDonator.get().getUser().getIdUser());
            donator.setIdDonator(idDonator);
            logger.info("Atualizando usuário: {}", gson.toJson(donator));

            donatorRepository.save(donator);

            return status(OK).build();
        }

        return status(NOT_FOUND).build();
    }

    @PatchMapping("password/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long idDonator,
                                               @RequestBody @Valid PasswordRequestDTO password) {
        Optional<Donator> findDonator = donatorRepository.findById(idDonator);
        if (findDonator.isPresent()) {
            userRepository.updatePassword(
                findDonator.get().getUser().getIdUser(),
                password.getPassword()
            );

            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonator(@PathVariable Long idDonator) {
        if (donatorRepository.existsById(idDonator)) {
            donatorRepository.deleteById(idDonator);
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
    public ResponseEntity<?> doRegister(@RequestBody @Valid Donator donator) {
        if (userRepository.existsByDocumentNumber(donator.getUser().getDocumentNumber())) {
            return status(BAD_REQUEST).body(
                    "CPF inválido, tente novamente com um cpf diferente"
            );
        }

        ResponseEntity<LatLongDTO> coordinates = addressDataService.getGeocoordinates(
                donator.getUser().getAddress()
        );

        donator.getUser().getAddress().setLatitude(
                Objects.requireNonNull(coordinates.getBody()).getLatitude()
        );

        donator.getUser().getAddress().setLongitude(
                Objects.requireNonNull(coordinates.getBody()).getLongitude()
        );

        try {
            logger.info(gson.toJson(donator));
            donatorRepository.save(
                    new Donator(
                            donator.getBloodType(),
                            donator.getUser()
                    )
            );

            return status(CREATED).build();

        }catch (Exception e) {
            logger.error("Erro de criação de usuário: \n\t{} \nErro: \n\t{}",
                    donator, e.toString()
            );
            return status(BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Donator> doLogin(@RequestBody @Valid DonatorLoginRequestDTO donator) {
        Optional<Donator> findDonator = donatorRepository.findByEmailAndPassword(
            donator.getEmail(),
            donator.getPassword()
        );

        if (findDonator.isPresent()) {
            userRepository.authenticateUser(findDonator.get().getUser().getIdUser());
            findDonator.get().getUser().setAuthenticated(true);
            return status(OK).body(findDonator.get());
        }
        return status(NOT_FOUND).build();
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity<?> doLogoff(@PathVariable Long idUser) {
        if (userRepository.existsById(idUser)) {
            userRepository.removeAuthenticationUser(idUser);
            return status(OK).build();
        }
        return status(BAD_REQUEST).build();
    }

    @GetMapping("/appointment/{idDonator}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentDays(
        @PathVariable Long idDonator
    ) {
        List<AppointmentResponseDTO> appointments = new ArrayList<>();
        List<Appointment> appointmentDays = appointmentRepository.findByDonatorIdDonator(
            idDonator
        );
        logger.info("Appointments: {}", appointmentDays);

        if (!appointmentDays.isEmpty()) {
            appointmentDays.forEach(
                it -> appointments.add(
                    AppointmentResponseDTO.builder()
                        .idAppointment(it.getIdAppointment())
                        .dhAppointment(it.getDhAppointment())
                        .accepted(it.isAccepted())
                        .idDonator(it.getDonator().getIdDonator())
                        .bloodType(it.getDonator().getBloodType())
                        .name(it.getDonator().getUser().getName())
                        .email(it.getDonator().getUser().getEmail())
                        .documentNumber(it.getDonator().getUser().getDocumentNumber())
                        .phone(it.getDonator().getUser().getPhone())
                        .idAddress(it.getDonator().getUser().getAddress().getIdAddress())
                        .address(it.getDonator().getUser().getAddress().getAddress())
                        .neighborhood(it.getDonator().getUser().getAddress().getNeighborhood())
                        .city(it.getDonator().getUser().getAddress().getCity())
                        .uf(it.getDonator().getUser().getAddress().getUf())
                        .cep(it.getDonator().getUser().getAddress().getCep())
                        .number(it.getDonator().getUser().getAddress().getNumber())
                        .idHospital(it.getHospital().getIdHospital())
                    .build()
                )
            );
            return status(OK).body(appointments);
        }

        return status(NOT_FOUND).build();
    }

    @PostMapping("/appointment")
    public ResponseEntity<Void> setAppointmentDay(@RequestBody AppointmentRequestDTO appointment) {
        Optional<Hospital> hospital = hospitalRepository.findById(appointment.getFkHospital());
        Optional<Donator> donator = donatorRepository.findById(appointment.getFkDonator());

        if (hospital.isPresent() && donator.isPresent()) {
            appointmentRepository.save(
                new Appointment(
                    appointment.getDhAppointment(),
                    donator.get(),
                    hospital.get()
                )
            );

            return status(CREATED).build();
        }
        return status(NOT_FOUND).build();
    }


    @DeleteMapping("/appointment/{idAppointment}")
    public ResponseEntity<?> deleteAppointmentDays(@PathVariable Long idAppointment) {
        if (appointmentRepository.existsById(idAppointment)) {
            appointmentRepository.deleteById(idAppointment);
            return status(OK).build();
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {

        Optional<Hospital> hospital = hospitalRepository.findById(id);
        if (hospital.isPresent()) {
            List<Hospital> lista = new ArrayList<>();
            lista.add(hospital.get());

            //String hospitalTxt = FileHandler.gravaArquivoTxt(lista, "hospital-" + hospital.get().getUser().getName());
            String hospitalTxt = " ";
            return ResponseEntity.status(200)
                    .header("content-type", "text/txt")
                    .header("content-disposition", "filename=\"hospital.txt\"")
                    .body(hospitalTxt);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
