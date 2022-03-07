package enterprise.hibisco.hibiscows.entities;

import enterprise.hibisco.hibiscows.service.DonatorService;

public class Donator extends User{

    private DonatorService donatorService = new DonatorService();

    private Long idDonator;
    private String nome;
    private String cpf;
    private String bloodType;


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
