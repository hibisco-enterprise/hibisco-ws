package enterprise.hibisco.hibiscows.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    @JsonIgnore
    private HospitalService hospitalService;

    @Autowired(required = true)
    private Hospital hospital;

    @PostMapping("/do-register")
    public ResponseEntity doRegister(@RequestBody Hospital hospital) {
        return hospitalService.doRegister(hospital);
    }

    @PostMapping("/do-login")
    public Hospital doLogin(@RequestBody Hospital hospital) {
        return hospital;
    }

    @PostMapping("/do-logoff/{email}")
    public String doLogoff(@PathVariable String email) {
        return hospitalService.doLogoff(email);
    }

}
