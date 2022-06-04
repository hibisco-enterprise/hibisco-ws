package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.HibiscoWsApplication;
import enterprise.hibisco.hibiscows.HibiscoWsApplicationTests;
import enterprise.hibisco.hibiscows.controller.DonatorController;
import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.DonatorRepository;
import enterprise.hibisco.hibiscows.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import static org.springframework.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {DonatorController.class})
class DonatorServiceTest {


    @Autowired
    private DonatorController donatorController;

    @MockBean
    private DonatorRepository donatorRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AddressRepository addressRepository;

    DonatorServiceTest() {
    }


//    @Test
//    @DisplayName("Teste Cadastro Doador - Deve retornar 404 (cadastro inválido)")
//    void doRegisterScenario1() {
//        Donator donator = mock(Donator.class);
//        ResponseEntity<?> resp = donatorController.doRegister(donator);
//
//        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
//
//    }
//
//    @Test
//    @DisplayName("Teste Cadastro Doador - Deve retornar 201 (cadastro realizado)")
//    void doRegisterScenario2() {
//    }

    @Test
    @DisplayName("Teste busca doador por Id - Deve retornar 404")
    void getDonatorByIdScenario1() {
        Donator donator = mock(Donator.class);
        donator.setIdDonator(1L);
        when(donatorRepository.findById(donator.getIdDonator())).thenReturn(Optional.empty());

        ResponseEntity<Optional<Donator>> resp = donatorController.getDonatorById(donator.getIdDonator());

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());

    }

    @Test
    void getDonatorByCpf() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void deleteDonator() {
    }

    @Test
    void doLogin() {
    }

    @Test
    void doLogoff() {
    }
}