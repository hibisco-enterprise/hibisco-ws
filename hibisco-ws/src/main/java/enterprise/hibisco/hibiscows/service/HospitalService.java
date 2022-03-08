package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    private List<Hospital> hospitals;

    public HospitalService() {
        this.hospitals = new ArrayList<>();
    }

    public String doRegister(User donator) {
        return "";
    }

    public String doLogin(String login, String password) {
        return "";
    }

    public String doLogoff(String email) {
        return "";
    }
}
