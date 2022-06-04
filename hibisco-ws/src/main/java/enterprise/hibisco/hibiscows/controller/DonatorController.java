package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.manager.FileHandler;
import enterprise.hibisco.hibiscows.repositories.HospitalRepository;
import enterprise.hibisco.hibiscows.request.DonatorLoginRequestDTO;
import enterprise.hibisco.hibiscows.request.PasswordRequestDTO;
import enterprise.hibisco.hibiscows.response.AppointmentResponseDTO;
import enterprise.hibisco.hibiscows.service.AppointmentService;
import enterprise.hibisco.hibiscows.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/donators")
@SuppressWarnings("unused")
public class DonatorController {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DonatorService donatorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Donator>> getDonators() {
        return donatorService.getDonators();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Donator>> getDonatorById(@PathVariable Long id) {
        return donatorService.getDonatorById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDonator(@PathVariable Long id, @RequestBody @Valid Donator donator) {
        return donatorService.updateDonator(id, donator);
    }

    @PatchMapping("password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id,
                                            @RequestBody @Valid PasswordRequestDTO password) {
        return donatorService.updatePassword(id, password.getPassword());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonator(@PathVariable Long id) {
        return donatorService.deleteDonator(id);
    }

    @GetMapping("/address/{idAddress}")
    public ResponseEntity<Optional<AddressData>> getAddressById(@PathVariable Long idAddress) {
        return donatorService.getAddressById(idAddress);
    }

    @PutMapping("/address/{idAddress}")
    public ResponseEntity<Optional<AddressData>> updateAddress(@PathVariable Long idAddress,
                                                               @RequestBody @Valid AddressData address) {
        return donatorService.updateAddressById(idAddress, address);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody @Valid Donator donator) {
        return donatorService.doRegister(donator);
    }

    @PostMapping("/login")
    public ResponseEntity<Donator> doLogin(@RequestBody @Valid DonatorLoginRequestDTO user) {
        return donatorService.doLogin(user);
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity<?> doLogoff(@PathVariable Long idUser) {
        return donatorService.doLogoff(idUser);
    }

    @GetMapping("/appointment/{idDonator}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentDays(@PathVariable Long idDonator) {
        return appointmentService.getAppointmentDays(idDonator);
    }

    @PostMapping("/appointment/{idDonator}/{fkAppointment}")
    public ResponseEntity<Void> setAppointment(@PathVariable Long idDonator,
                                            @PathVariable Long fkAppointment) {
        return appointmentService.setAppointmentDay(fkAppointment, idDonator);
    }

    @DeleteMapping("/appointment/{idAppointment}")
    public ResponseEntity<?> deleteAppointmentDays(@PathVariable Long idAppointment) {
        return appointmentService.cancelAppointmentDay(idAppointment);
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {

        Optional<Hospital> hospital = hospitalRepository.findById(id);
        if (hospital.isPresent()) {
            List<Hospital> lista = new ArrayList<>();
            lista.add(hospital.get());

            String hospitalTxt = FileHandler.gravaArquivoTxt(lista, "hospital-" + hospital.get().getUser().getName());

            return ResponseEntity.status(200)
                    .header("content-type", "text/txt")
                    .header("content-disposition", "filename=\"hospital.txt\"")
                    .body(hospitalTxt);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
