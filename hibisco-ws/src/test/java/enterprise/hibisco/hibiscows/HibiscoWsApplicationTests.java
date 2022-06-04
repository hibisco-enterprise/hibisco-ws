package enterprise.hibisco.hibiscows;

import enterprise.hibisco.hibiscows.controller.DonatorController;
import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.request.DonatorLoginRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class HibiscoWsApplicationTests {

    @Autowired
    private DonatorController controller;

    @Test
    void contextLoads() {

    }

	@Test
	public void loginAndRegisterDonatorShouldReturnOK() {
        //teste cadastro
		Donator donatorTest =
            Donator.builder()
                .bloodType("AB+")
                .user(
                    User.builder()
                        .name("Cavalo brabo")
                        .email("cavalo.brabo@gmail.com")
                        .password("Teste@123")
                        .documentNumber("971.709.728-32")
                        .phone("(11) 98137-4499")
                        .address(
                            AddressData.builder()
                                .address("rua da paixão")
                                .neighborhood("Vila das bonitezas")
                                .city("Cidade Alta")
                                .uf("RJ")
                                .cep("08534-300")
                                .number(29)
                            .build())
                    .build())
            .build();
        ResponseEntity<?> testRegister = controller.doRegister(donatorTest);
        assert(testRegister.getStatusCode().equals(CREATED));

        // teste login
        DonatorLoginRequestDTO login =
            DonatorLoginRequestDTO.builder()
                .email("cavalo.brabo@gmail.com")
                .password("Teste@123")
        .build();

        ResponseEntity<?> testLogin = controller.doLogin(login);
        assert(testLogin.getStatusCode().equals(OK));
	}


}
