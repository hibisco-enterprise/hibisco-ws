package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.responses.DonatorResponseDTO;
import enterprise.hibisco.hibiscows.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/donators")
@SuppressWarnings("unused")
public class DonatorController {

    @Autowired
    private DonatorService donatorService;

    @PostMapping("/register")
    public ResponseEntity doRegister(@RequestBody @Valid DonatorResponseDTO donator) {
        return donatorService.doRegister(donator);
    }

    @GetMapping
    public ResponseEntity getDonators() {
        return donatorService.getDonators();
    }
    
    @PostMapping("/login")
    public ResponseEntity doLogin(@RequestBody @Valid DonatorResponseDTO user) {
        return donatorService.doLogin(user);
    }

    @DeleteMapping("/logoff/{idUser}")
    public ResponseEntity doLogoff(@PathVariable Long idUser) {
        return donatorService.doLogoff(idUser);
    }

}
