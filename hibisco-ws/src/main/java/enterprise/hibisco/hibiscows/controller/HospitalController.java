package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.HospitalAppointment;
import enterprise.hibisco.hibiscows.request.AvaliableDaysWrapperRequestDTO;
import enterprise.hibisco.hibiscows.request.HospitalLoginRequestDTO;
import enterprise.hibisco.hibiscows.request.PasswordRequestDTO;
import enterprise.hibisco.hibiscows.response.AvaliableDaysResponseDTO;
import enterprise.hibisco.hibiscows.rest.positionstack.ClientPositionStack;
import enterprise.hibisco.hibiscows.rest.positionstack.PositionStackResponse;
import enterprise.hibisco.hibiscows.service.AddressDataService;
import enterprise.hibisco.hibiscows.service.HospitalAppointmentService;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hospitals")
@SuppressWarnings("unused")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalAppointmentService appointmentService;

    @Autowired
    private AddressDataService addressDataService;

    @Autowired
    private ClientPositionStack clientPositionStack;

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

    @PatchMapping("password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id,
                                            @RequestBody @Valid PasswordRequestDTO password) {
        return hospitalService.updatePassword(id, password.getPassword());
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
    public ResponseEntity<?> doRegister(@RequestBody @Valid Hospital hospital) {
        return hospitalService.doRegister(hospital);
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody @Valid HospitalLoginRequestDTO user) {
        return hospitalService.doLogin(user);
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity<?> doLogoff(@PathVariable Long idUser) {
        return hospitalService.doLogoff(idUser);
    }

    @GetMapping("/appointment/{idHospital}")
    public ResponseEntity<List<AvaliableDaysResponseDTO>> getAvaliableDays(@PathVariable Long idHospital) {
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

    @PostMapping("/appointment/accept/{idAppointment}")
    public ResponseEntity<?> acceptAvailableDay(@PathVariable Long idAppointment) {
        return appointmentService.acceptAppointmentDay(idAppointment);
    }

}
