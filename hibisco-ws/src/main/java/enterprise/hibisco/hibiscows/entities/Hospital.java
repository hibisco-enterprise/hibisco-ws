package enterprise.hibisco.hibiscows.entities;
import enterprise.hibisco.hibiscows.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ThreadLocalRandom;

public class Hospital extends User{

    @Autowired
    private HospitalService hospitalService;

    private Long idHospital;
    private String nameHospital;
    private String cnpjHospital;

    public Hospital() {
        this.idHospital = ThreadLocalRandom.current().nextLong(10000, 99999);
    }

    @Override
    public ResponseEntity doRegister(User hospital) {
        return hospitalService.doRegister(hospital);
    }

    @Override
    public ResponseEntity doLogin(User user) {
        return hospitalService.doLogin(user);
    }

    @Override
    public ResponseEntity doLogoff(String login) {
        return hospitalService.doLogoff(login);
    }

    @Override
    public String getDocument() {
        return getCnpjHospital();
    }

    public Long getIdHospital() {
        return idHospital;
    }

    public String getNameHospital() {
        return nameHospital;
    }

    public void setNameHospital(String nameHospital) {
        this.nameHospital = nameHospital;
    }

    public String getCnpjHospital() {
        return cnpjHospital;
    }

    public void setCnpjHospital(String cnpjHospital) {
        this.cnpjHospital = cnpjHospital;
    }
}
