package enterprise.hibisco.hibiscows.entities;

import enterprise.hibisco.hibiscows.service.DonatorService;
import enterprise.hibisco.hibiscows.utils.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;

public class Donator extends User{

    @Autowired
    private DonatorService donatorService;

    private Long idDonator;
    private String nome;
    private String cpf;
    private String bloodType;

    public Donator() {
        this.idDonator = UuidGenerator.wrapUuid();
    }

    @Override
    public String doRegister(User donator) {
        return donatorService.doRegister(donator);
    }

    @Override
    public String doLogin(String login, String password) {
        return donatorService.doLogin(login, password);
    }

    public DonatorService getDonatorService() {
        return donatorService;
    }

    public Long getIdDonator() {
        return idDonator;
    }

    public void setIdDonator(Long idDonator) {
        this.idDonator = idDonator;
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
