package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonatorService {

    private List<User> donators;

    public DonatorService() {
        this.donators = new ArrayList<>();
    }

    public ResponseEntity doRegister(User donator) {
        for (User user: donators) {
            if (user.getDocument().equals(donator.getDocument())){
                return ResponseEntity.status(401).body("CPF já existe no sistema!");
            }
        }

        if (donator.getDocument().length() != 11) {
            return ResponseEntity.status(401).body("Número de CPF inválido!");
        }

        if (!donator.getEmail().contains("@") && !donator.getEmail().contains(".com") && donator.getEmail().length() < 5) {
            return ResponseEntity.status(401).body("Formato de e-mail inválido.");
        }

        if (!donator.validatePassword()) {
            return ResponseEntity.status(401).body("Senha inválida.");
        }


        donators.add(donator);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity getDonators() {
        return ResponseEntity.status(donators.isEmpty() ? 204 : 200).body(donators);
    }

    public ResponseEntity doLogin(User donator) {
        for (User u: donators) {
            if (u.authenticate(donator)) {
                u.setAuthenticated(true);
                return ResponseEntity.status(200).build();
            }
        }

        return ResponseEntity.status(401).build();
    }

    public ResponseEntity doLogoff(String email) {
        for (User user : donators) {
            if (user.getEmail().equals(email)) {
                user.setAuthenticated(false);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.status(404).build();
    }

}
