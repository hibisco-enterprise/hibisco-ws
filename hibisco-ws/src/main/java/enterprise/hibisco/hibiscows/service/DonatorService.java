package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonatorService {

    @Autowired
    private Donator donator;

    public String doRegister(User donator) {
        return "";
    }

    public String doLogin(String login, String password) {
        return "";
    }

}
