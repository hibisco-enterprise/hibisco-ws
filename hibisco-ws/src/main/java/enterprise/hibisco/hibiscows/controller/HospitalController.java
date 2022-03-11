package enterprise.hibisco.hibiscows.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    @JsonIgnore
    private HospitalService hospitalService;

    @PostMapping("/do-register")
    public ResponseEntity doRegister(@RequestBody Hospital hospital) {
        return hospitalService.doRegister(hospital);
    }

    @GetMapping
    public List<User> getHospitals() {
        return hospitalService.getHospitals();
    }

    @PostMapping("/do-login/{email}/{password}")
    public String doLogin(@PathVariable String email, @PathVariable String password) {
        return hospitalService.doLogin(email, password);
    }

    @PostMapping("/do-logoff/{email}")
    public String doLogoff(@PathVariable String email) {
        return hospitalService.doLogoff(email);
    }

}
