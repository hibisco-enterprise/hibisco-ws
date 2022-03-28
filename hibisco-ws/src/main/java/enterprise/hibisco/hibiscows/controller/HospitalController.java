package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.responses.UserResponseDTO;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/register")
    public ResponseEntity doRegister(@RequestBody UserResponseDTO hospital) {
        return hospitalService.doRegister(hospital);
    }

    @GetMapping
    public ResponseEntity getHospitals() {
        return hospitalService.getHospitals();
    }

    @PostMapping("/login")
    public ResponseEntity doLogin(@RequestBody UserResponseDTO user) {
        return hospitalService.doLogin(user);
    }

    @DeleteMapping("/logoff/{email}")
    public ResponseEntity doLogoff(@PathVariable String email) {
        return hospitalService.doLogoff(email);
    }

}
