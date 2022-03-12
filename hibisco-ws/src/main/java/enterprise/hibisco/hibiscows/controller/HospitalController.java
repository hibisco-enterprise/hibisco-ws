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
    private HospitalService hospitalService;

    @PostMapping("/register")
    public ResponseEntity doRegister(@RequestBody Hospital hospital) {
        return hospitalService.doRegister(hospital);
    }

    @GetMapping("/list")
    public List<User> getHospitals() {
        return hospitalService.getHospitals();
    }

    @PostMapping("/login")
    public ResponseEntity doLogin(@RequestBody User user) {
        return hospitalService.doLogin(user);
    }

    @DeleteMapping("/logoff/{email}")
    public ResponseEntity doLogoff(@PathVariable String email) {
        return hospitalService.doLogoff(email);
    }

}
