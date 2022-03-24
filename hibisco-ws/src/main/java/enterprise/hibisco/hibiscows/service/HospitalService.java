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
        hospitals.add(hospital);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity getHospitals() {
        return ResponseEntity.status(hospitals.isEmpty() ? 204 : 200).body(hospitals);
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
