package enterprise.hibisco.hibiscows.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donators")
public class DonatorController {

    @Autowired
    @JsonIgnore
    private DonatorService donatorService;

    @PostMapping("/register")
    public ResponseEntity doRegister(@RequestBody Donator donator) {
        return donatorService.doRegister(donator);
    }

    @GetMapping
    public List<User> getDonators() {
        return donatorService.getDonators();
    }
    
    @PostMapping("/login")
    public ResponseEntity doLogin(@RequestBody User user) {
        return donatorService.doLogin(user);
    }

    @DeleteMapping("/logoff/{email}")
    public String doLogoff(@PathVariable String email) {
        return donatorService.doLogoff(email);
    }

}
