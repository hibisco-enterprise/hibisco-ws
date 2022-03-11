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

    public List<User> getHospitals() {
        return hospitals;
    }

    public ResponseEntity doLogin(User hospital) {
        hospital.setAuthenticated(hospital.autenticar(hospital));
        return ResponseEntity.status(hospital.isAuthenticated() ? 200 : 401).build();
    }

    public String doLogoff(String email) {
        for (User user : hospitals) {
            if (user.getEmail().equals(email)) {
                user.setAuthenticated(false);
                return "Logoff efetuado com sucesso!";
            }
        }
        return "Usuário não encontrado. Tente novamente";
    }
}
