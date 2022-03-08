package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonatorService {

    private List<User> donators;

    public DonatorService() {
        this.donators = new ArrayList<>();
    }

    public String doRegister(User donator) {
        for (User user: donators) {
            Donator doador = ((Donator) donator);
            Donator usuario = ((Donator) user);
            if (doador.getCpf().equals(usuario.getCpf())){
                return "CPF já existe no sistema!";
            }
        }
        donators.add(donator);
        return "Usuário cadastrado com sucesso!";
    }

    public String doLogin(String email, String password) {
        for (User user : donators) {
            if (user.getEmail().equals(email) && user.recuperarPassword().equals(password)) {
                user.setAuthenticated(true);
                return "Login efetuado com sucesso!";
            }
        }
        return "Usuário não encontrado. Tente novamente";
    }

    public String doLogoff(String email) {
        for (User user : donators) {
            if (user.getEmail().equals(email)) {
                user.setAuthenticated(false);
                return "Logoff efetuado com sucesso!";
            }
        }
        return "Usuário não encontrado. Tente novamente";
    }

}
