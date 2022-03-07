package enterprise.hibisco.hibiscows.entities;
import com.github.javafaker.Faker;
import java.util.Locale;

public abstract class User {

    private String email;
    private String password;
    private String phone;
    private String address; // por enquanto está sendo gerado automaticamente

    public User() {
        this.address = new Faker(new Locale("pt-BR")).address().fullAddress();
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
        return "\t" + address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract String doRegister(User user);

    public abstract String doLogin(String email, String password);

}
