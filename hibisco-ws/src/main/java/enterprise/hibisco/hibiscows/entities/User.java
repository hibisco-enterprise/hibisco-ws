package enterprise.hibisco.hibiscows.entities;
import com.github.javafaker.Faker;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public abstract class User {

    private String email;
    private String password;
    private String phone;
    private String address; // por enquanto está sendo gerado automaticamente
    private boolean authenticated;

    public User() {
        this.address = new Faker(new Locale("pt-BR")).address().fullAddress();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean autenticar(User user) {
        return this.email.equals(user.getEmail()) &&
               this.password.equals(user.recuperarPassword());
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String recuperarPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public abstract ResponseEntity doRegister(User user);

    public abstract ResponseEntity doLogin(User user);

    public abstract ResponseEntity doLogoff(String email);

    public abstract String getDocument();
}
