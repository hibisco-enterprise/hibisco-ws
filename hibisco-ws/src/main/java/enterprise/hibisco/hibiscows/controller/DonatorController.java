package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Appointment;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.request.DonatorRequestDTO;
import enterprise.hibisco.hibiscows.service.AppointmentService;
import enterprise.hibisco.hibiscows.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/donators")
@SuppressWarnings("unused")
public class DonatorController {

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
    public ResponseEntity<?> doRegister(@RequestBody @Valid DonatorRequestDTO donator) {
        return donatorService.doRegister(donator);
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody @Valid DonatorRequestDTO user) {
        return donatorService.doLogin(user);
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity<?> doLogoff(@PathVariable Long idUser) {
        return donatorService.doLogoff(idUser);
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id){
        return donatorService.getReport(id);
    }

    @GetMapping("/appointment/{idDonator}/{idHospital}")
    public ResponseEntity<List<Appointment>> getAppointmentDays(@PathVariable Long idDonator,
                                                                @PathVariable Long idHospital) {
        return appointmentService.getAppointmentDays(idDonator, idHospital);}

    @PostMapping("/appointment/{idDonator}/{fkAppointment}")
    public ResponseEntity<?> setAppointment(@PathVariable Long idDonator, @PathVariable Long fkAppointment) {
        return appointmentService.setAppointmentDay(fkAppointment, idDonator); }
}
