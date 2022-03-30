package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.responses.HospitalResponseDTO;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospitals")
@SuppressWarnings("unused")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/register")
    public ResponseEntity doRegister(@RequestBody HospitalResponseDTO hospital) {
        return hospitalService.doRegister(hospital);
    }

    @GetMapping
    public ResponseEntity getHospitals() {
        return hospitalService.getHospitals();
    }

    @PostMapping("/login")
    public ResponseEntity doLogin(@RequestBody HospitalResponseDTO user) {
        return hospitalService.doLogin(user);
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity doLogoff(@PathVariable Long idUser) {
        return hospitalService.doLogoff(idUser);
    }

}
