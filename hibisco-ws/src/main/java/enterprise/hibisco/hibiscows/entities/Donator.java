package enterprise.hibisco.hibiscows.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import enterprise.hibisco.hibiscows.service.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ThreadLocalRandom;

public class Donator extends User{

    @Autowired
    @JsonIgnore
    private DonatorService donatorService;

    private Long idDonator;
    private String nome;
    private String cpf;
    private String bloodType;

    public Donator() {
        this.idDonator = ThreadLocalRandom.current().nextLong(10000, 99999);
    }

    @Override
    public ResponseEntity doRegister(User donator) {
        return donatorService.doRegister(donator);
    }

    @Override
    public String doLogin(String email, String passsword) {
        return donatorService.doLogin(email, passsword);
    }

    @Override
    public String doLogoff(String email) {
        return donatorService.doLogoff(email);
    }

    public Long getIdDonator() {
        return idDonator;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
