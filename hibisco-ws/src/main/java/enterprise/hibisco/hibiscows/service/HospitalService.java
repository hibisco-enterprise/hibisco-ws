package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Donator;
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
        for (User user : hospitals) {
            Hospital bloodCenter = ((Hospital) hospital);
            Hospital hosp = ((Hospital) user);
            if (bloodCenter.getCnpjHospital().equals(hosp.getCnpjHospital())){
                return ResponseEntity.status(401).body("CNPJ já existe no sistema!");
            }
        }
        hospitals.add(hospital);
        return ResponseEntity.status(201).build();
    }

    public List<User> getHospitals() {
        return hospitals;
    }

    public String doLogin(String email, String password) {
        for (User user : hospitals) {
            if (
                user.getEmail().equals(email) &&
                user.recuperarPassword().equals(password)
            ) {
                user.setAuthenticated(true);
                return "Login efetuado com sucesso!";
            }
        }
        return "Usuário não encontrado. Tente novamente";
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
