package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.request.AvaliableDaysWrapperRequestDTO;
import enterprise.hibisco.hibiscows.request.HospitalRequestDTO;
import enterprise.hibisco.hibiscows.service.HospitalAppointmentService;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hospitals")
@SuppressWarnings("unused")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalAppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Hospital>> getHospitals() {
        return hospitalService.getHospitals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Hospital>> getHospitalById(@PathVariable Long id) {
        return hospitalService.getHospitalById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHospital(@PathVariable Long id, @RequestBody @Valid Hospital hospital) {
        return hospitalService.updateHospital(id, hospital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHospital(@PathVariable Long id) {
        return hospitalService.deleteHospital(id);
    }

    @GetMapping("/address/{idAddress}")
    public ResponseEntity<Optional<AddressData>> getAddressById(@PathVariable Long idAddress) {
        return hospitalService.getAddressById(idAddress);
    }

    @PutMapping("/address/{idAddress}")
    public ResponseEntity<Optional<AddressData>> updateAddress(@PathVariable Long idAddress,
                                                               @RequestBody @Valid AddressData address) {
        return hospitalService.updateAddressById(idAddress, address);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody @Valid HospitalRequestDTO hospital) {
        return hospitalService.doRegister(hospital);
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody @Valid HospitalRequestDTO user) {
        return hospitalService.doLogin(user);
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity<?> doLogoff(@PathVariable Long idUser) {
        return hospitalService.doLogoff(idUser);
    }

    @GetMapping("/appointment/{idHospital}")
    public ResponseEntity<List<HospitalAppointment>> getAvaliableDays(@PathVariable Long idHospital) {
        return appointmentService.getAvaliableDays(idHospital);
    }

    @PostMapping("/appointment/{idHospital}")
    public ResponseEntity<?> setAvaliableDays(@PathVariable Long idHospital,
                                              @RequestBody @Valid AvaliableDaysWrapperRequestDTO avaliableDays) {
        return appointmentService.setAvaliableDays(idHospital, avaliableDays);
    }

    @DeleteMapping("/appointment/{idHospitalAppointment}")
    public ResponseEntity<?> deleteAvaliableDay(@PathVariable Long idHospitalAppointment) {
        return appointmentService.deleteAvaliableDay(idHospitalAppointment);
    }
}
