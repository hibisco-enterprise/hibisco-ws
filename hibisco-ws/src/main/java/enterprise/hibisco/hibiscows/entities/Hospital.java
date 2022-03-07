package enterprise.hibisco.hibiscows.entities;
import enterprise.hibisco.hibiscows.service.DonatorService;
import enterprise.hibisco.hibiscows.service.HospitalService;
import enterprise.hibisco.hibiscows.utils.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;

public class Hospital extends User{

    @Autowired
    private HospitalService hospitalService;

    private Long idHospital;
    private String nameHospital;
    private String cnpjHospital;

    public Hospital() {
        this.idHospital = UuidGenerator.wrapUuid().longValue();
    }

    @Override
    public String doRegister(User donator) {
        return hospitalService.doRegister(donator);
    }

    @Override
    public String doLogin(String login, String password) {
        return hospitalService.doLogin(login, password);
    }

}
