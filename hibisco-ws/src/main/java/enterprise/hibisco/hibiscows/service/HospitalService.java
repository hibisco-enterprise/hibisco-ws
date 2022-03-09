package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    private List<User> hospitals;

    public HospitalService() {
        this.hospitals = new ArrayList<>();
    }

    public String doRegister(User hospital) {
        for (User user : hospitals) {
            Hospital bloodCenter = ((Hospital) hospital);
            Hospital hosp = ((Hospital) user);
            if (bloodCenter.getCnpjHospital().equals(hosp.getCnpjHospital())){
                return "CNPJ já existe no sistema!";
            }
        }
        hospitals.add(hospital);
        return "Usuário cadastrado com sucesso!";
    }

    public String doLogin(String email, String password) {
        for (User user : hospitals) {
            if (user.getEmail().equals(email) && user.recuperarPassword().equals(password)) {
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
