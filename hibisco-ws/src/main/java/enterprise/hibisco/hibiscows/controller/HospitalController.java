package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.Hospital;
import request.HospitalResponseDTO;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hospitals")
@SuppressWarnings("unused")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody @Valid HospitalResponseDTO hospital) {
        return hospitalService.doRegister(hospital);
    }

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

    @PostMapping("/login")
    public ResponseEntity doLogin(@RequestBody @Valid HospitalResponseDTO user) {
        return hospitalService.doLogin(user);
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity doLogoff(@PathVariable Long idUser) {
        return hospitalService.doLogoff(idUser);
    }
}
