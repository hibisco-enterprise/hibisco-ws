package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.repositories.*;
import enterprise.hibisco.hibiscows.request.DonatorLoginRequestDTO;
import enterprise.hibisco.hibiscows.request.PasswordRequestDTO;
import enterprise.hibisco.hibiscows.service.AddressDataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.springframework.http.HttpStatus.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
@SpringBootTest(classes = {DonatorController.class})
class DonatorControllerTest {
    @Autowired
    private DonatorController controller;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DonatorRepository donatorRepository;

    @MockBean
    private AddressDataService addressDataService;

    @MockBean
    private HospitalRepository hospitalRepository;

    @MockBean
    private AppointmentRepository appointmentRepository;

    @Test
    @DisplayName("getDonators() - Sem corpo e deve retornar 204 - No Content")
    void getDonatorsScenario1() {
        when(donatorRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Donator>> resp = controller.getDonators();

        assertEquals(NO_CONTENT, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test()
    @DisplayName("getDonators() - Com corpo (2 registros) e deve retornar 200 - Ok")
    void getDonatorsScenario2() {
        Donator donatorMock1 = mock(Donator.class);
        Donator donatorMock2 = mock(Donator.class);
        when(donatorRepository.findAll()).thenReturn(List.of(donatorMock1, donatorMock2));
        when(donatorRepository.count()).thenReturn(2L);
        ResponseEntity<List<Donator>> resp = controller.getDonators();

        assertEquals(OK, resp.getStatusCode());
        assertEquals(List.of(donatorMock1, donatorMock2), resp.getBody());
    }

    @Test
    @DisplayName("getDonatorById() - Sem corpo e deve retornar 404 - Not Found")
    void getDonatorByIdScenario1() {
        Donator donatorMock = mock(Donator.class);
        donatorMock.setIdDonator(1L);
        when(donatorRepository.findById(donatorMock.getIdDonator())).thenReturn(Optional.empty());

        ResponseEntity<Optional<Donator>> resp = controller.getDonatorById(donatorMock.getIdDonator());
        assertEquals(NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    @DisplayName("getDonatorById() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void getDonatorByIdScenario2() {
        Donator donatorMock = mock(Donator.class);
        donatorMock.setIdDonator(1L);
        when(donatorRepository.findById(donatorMock.getIdDonator())).thenReturn(Optional.of(donatorMock));

        ResponseEntity<Optional<Donator>> resp = controller.getDonatorById(donatorMock.getIdDonator());
        assertEquals(OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }

    @Test
    @DisplayName("getDonatorByCpf() - Sem corpo e deve retornar 404 - Not Found")
    void getDonatorByCpfScenario1() {
        Donator donatorMock = mock(Donator.class, RETURNS_DEEP_STUBS);
        when(donatorMock.getUser().getDocumentNumber()).thenReturn("478.857.409-86");

        when(
            donatorRepository.findByUserDocumentNumberIgnoreCase(
                donatorMock.getUser().getDocumentNumber()
            )
        ).thenReturn(Optional.empty());

        ResponseEntity<Optional<Donator>> resp = controller.getDonatorByCpf(
            donatorMock.getUser().getDocumentNumber()
        );

        assertEquals(NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    @DisplayName("getDonatorByCpf() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void getDonatorByCpfScenario2() {
        Donator donatorMock = mock(Donator.class, RETURNS_DEEP_STUBS);
        when(donatorMock.getUser().getDocumentNumber()).thenReturn("478.857.409-86");

        when(
            donatorRepository.findByUserDocumentNumberIgnoreCase(
                donatorMock.getUser().getDocumentNumber()
            )
        ).thenReturn(Optional.of(donatorMock));

        ResponseEntity<Optional<Donator>> resp = controller.getDonatorByCpf(
            donatorMock.getUser().getDocumentNumber()
        );

        assertEquals(OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }

    @Test
    @DisplayName("updatePassword() - Sem corpo e deve retornar 404 - Not Found")
    void updatePasswordScenario1() {
        Donator donatorMock = mock(Donator.class, RETURNS_DEEP_STUBS);
        PasswordRequestDTO newPass = mock(PasswordRequestDTO.class);

        when(
            donatorRepository.findById(
                donatorMock.getIdDonator()
            )
        ).thenReturn(Optional.empty());

        when(
            userRepository.updatePassword(donatorMock.getUser().getIdUser(), newPass.getPassword())
        ).thenReturn(0);

        ResponseEntity<Void> resp = controller.updatePassword(donatorMock.getIdDonator(), newPass);

        assertEquals(NOT_FOUND, resp.getStatusCode());
    }

    @Test
    @DisplayName("updatePassword() - Sem corpo e deve retornar 200 - Ok")
    void updatePasswordScenario2() {
        Donator donatorMock = mock(Donator.class, RETURNS_DEEP_STUBS);
        PasswordRequestDTO newPass = mock(PasswordRequestDTO.class);

        when(
            donatorRepository.findById(
                donatorMock.getIdDonator()
            )
        ).thenReturn(Optional.of(donatorMock));

        when(
            userRepository.updatePassword(donatorMock.getUser().getIdUser(), newPass.getPassword())
        ).thenReturn(1);

        ResponseEntity<Void> resp = controller.updatePassword(donatorMock.getIdDonator(), newPass);

        assertEquals(OK, resp.getStatusCode());
    }

    @Test
    @DisplayName("doLogin() - Sem corpo e deve retornar 404 - Not Found")
    void doLoginScenario1() {
        Donator donatorMock = mock(Donator.class, RETURNS_DEEP_STUBS);
        when(
            donatorRepository.findByEmailAndPassword(
                donatorMock.getUser().getEmail(),
                donatorMock.getUser().getPassword()
            )
        ).thenReturn(Optional.empty());

        when(
            userRepository.authenticateUser(
                donatorMock.getUser().getIdUser()
            )
        ).thenReturn(0);

        ResponseEntity<Donator> resp = controller.doLogin(
            new DonatorLoginRequestDTO(
                donatorMock.getUser().getEmail(),
                donatorMock.getUser().getPassword()
            )
        );

        assertEquals(NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());

    }

    @Test
    @DisplayName("doLogin() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void doLoginScenario2() {
        Donator donatorMock = mock(Donator.class, RETURNS_DEEP_STUBS);
        when(
            donatorRepository.findByEmailAndPassword(
                donatorMock.getUser().getEmail(),
                donatorMock.getUser().getPassword()
            )
        ).thenReturn(Optional.of(donatorMock));

        when(
            userRepository.authenticateUser(
                donatorMock.getUser().getIdUser()
            )
        ).thenReturn(1);

        ResponseEntity<Donator> resp = controller.doLogin(
            new DonatorLoginRequestDTO(
                donatorMock.getUser().getEmail(),
                donatorMock.getUser().getPassword()
            )
        );

        assertEquals(OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }
}