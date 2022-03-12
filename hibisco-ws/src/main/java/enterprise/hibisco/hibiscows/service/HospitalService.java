package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    private List<User> hospitals;

    public HospitalService() {
        this.hospitals = new ArrayList<>();
    }

    public ResponseEntity doRegister(User hospital) {
        for (User bloodCenter : hospitals) {
            if (bloodCenter.getDocument().equals(hospital.getDocument())){
                return ResponseEntity.status(401).body("CNPJ já existe no sistema!");
            }
        }

        if (hospital.getDocument().length() != 11) {
            return ResponseEntity.status(401).body("Número de CPF inválido!");
        }

        if (!hospital.getEmail().contains("@")) {
            return ResponseEntity.status(401).body("Formato de e-mail inválido. O e-mail deve possuir @");
        }

        if (!hospital.getEmail().contains(".com")) {
            return ResponseEntity.status(401).body("Formato de e-mail inválido. O e-mail deve possuir .com");
        }

        if (hospital.getEmail().length() < 6) {
            return ResponseEntity.status(401).body("Formato de e-mail inválido. O e-mail deve ter mais de 6 caracteres.");
        }

        if (!hospital.validatePassword()) {
            return ResponseEntity.status(401).body("Senha inválida.");
        }

        hospitals.add(hospital);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity getHospitals() {
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity doLogin(User hospital) {
        for (User u: hospitals) {
            if (u.authenticate(hospital)) {
                u.setAuthenticated(true);
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(401).build();
    }

    public ResponseEntity doLogoff(String email) {
        for (User user : hospitals) {
            if (user.getEmail().equals(email)) {
                user.setAuthenticated(false);
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(404).build();
    }
}
