package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonatorService {

    private List<User> users;

    public DonatorService() {
        this.users = new ArrayList<>();
    }

    public String doRegister(User donator) {
        return "";
    }

    public String doLogin(String login, String password) {
        return "";
    }

}
