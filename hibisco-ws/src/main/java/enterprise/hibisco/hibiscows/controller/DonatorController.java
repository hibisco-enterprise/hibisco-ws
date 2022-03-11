package enterprise.hibisco.hibiscows.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donators")
public class DonatorController {

    @Autowired
    @JsonIgnore
    private DonatorService donatorService;

    @PostMapping("/do-register")
    public ResponseEntity doRegister(@RequestBody Donator donator) {
        return donatorService.doRegister(donator);
    }

    @PostMapping("/do-login")
    public String doLogin(@RequestBody Donator donator) {
        return donatorService.doLogin(donator);
    }

    @PostMapping("/do-logoff/{email}")
    public String doLogoff(@PathVariable String email) {
        return donatorService.doLogoff(email);
    }

}
