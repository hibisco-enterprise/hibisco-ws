package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Hospital;
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
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity doLogin(User hospital) {
        hospital.setAuthenticated(hospital.autenticar(hospital));
        return ResponseEntity.status(hospital.isAuthenticated() ? 200 : 401).build();
    }

    public ResponseEntity doLogoff(String email) {
        for (User user : hospitals) {
            if (user.getEmail().equals(email)) {
                user.setAuthenticated(true);
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(404).build();
    }
}
