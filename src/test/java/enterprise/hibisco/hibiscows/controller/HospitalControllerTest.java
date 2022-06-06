package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.Donator;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.repositories.*;
import enterprise.hibisco.hibiscows.request.HospitalLoginRequestDTO;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = {HospitalController.class})
@SuppressWarnings("unused")
class HospitalControllerTest {

    @Autowired
    private HospitalController controller;

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

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private BloodStockRepository bloodStockRepository;


    @Test
    @DisplayName("getHospitals() - Sem corpo e deve retornar 204 - No Content")
    void getHospitalsScenario1() {
        when(hospitalRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Hospital>> resp = controller.getHospitals();

        assertEquals(NO_CONTENT, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    @DisplayName("getHospitals() - Com corpo (2 registros) e deve retornar 200 - Ok")
    void getHospitalsScenario2() {
        Hospital hospitalMock1 = mock(Hospital.class);
        Hospital hospitalMock2 = mock(Hospital.class);
        when(hospitalRepository.findAll()).thenReturn(List.of(hospitalMock1, hospitalMock2));
        when(hospitalRepository.count()).thenReturn(2L);
        ResponseEntity<List<Hospital>> resp = controller.getHospitals();
    }

    @Test
    @DisplayName("getHospitalById() - Sem corpo e deve retornar 404 - Not Found")
    void getHospitalByIdScenario1() {
        Hospital hospitalMock = mock(Hospital.class);
        hospitalMock.setIdHospital(1L);
        when(hospitalRepository.findById(hospitalMock.getIdHospital())).thenReturn(Optional.empty());

        ResponseEntity<Optional<Hospital>> resp = controller.getHospitalById(hospitalMock.getIdHospital());
        assertEquals(NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    @DisplayName("getHospitalById() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void getHospitalByIdScenario2() {
        Hospital hospitalMock = mock(Hospital.class);
        hospitalMock.setIdHospital(1L);
        when(hospitalRepository.findById(hospitalMock.getIdHospital())).thenReturn(Optional.of(hospitalMock));

        ResponseEntity<Optional<Hospital>> resp = controller.getHospitalById(hospitalMock.getIdHospital());
        assertEquals(OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }

    @Test
    @DisplayName("getHospitalByCnpj() - Sem corpo e deve retornar 404 - Not Found")
    void getDonatorByCnpjScenario1() {
        Hospital hospitalMock = mock(Hospital.class, RETURNS_DEEP_STUBS);
        when(hospitalMock.getUser().getDocumentNumber()).thenReturn("04.496.958/0001-76");

        when(
            hospitalRepository.findByUserDocumentNumberIgnoreCase(
                hospitalMock.getUser().getDocumentNumber()
            )
        ).thenReturn(Optional.empty());

        ResponseEntity<Optional<Hospital>> resp = controller.getHospitalByCnpj(
            hospitalMock.getUser().getDocumentNumber()
        );

        assertEquals(NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    @DisplayName("getHospitalByCnpj() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void getDonatorByCnpjScenario2() {
        Hospital hospitalMock = mock(Hospital.class, RETURNS_DEEP_STUBS);
        when(hospitalMock.getUser().getDocumentNumber()).thenReturn("04.496.958/0001-76");

        when(
            hospitalRepository.findByUserDocumentNumberIgnoreCase(
                hospitalMock.getUser().getDocumentNumber()
            )
        ).thenReturn(Optional.of(hospitalMock));

        ResponseEntity<Optional<Hospital>> resp = controller.getHospitalByCnpj(
            hospitalMock.getUser().getDocumentNumber()
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
            hospitalRepository.findById(
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
        Hospital hospitalMock = mock(Hospital.class, RETURNS_DEEP_STUBS);
        PasswordRequestDTO newPass = mock(PasswordRequestDTO.class);

        when(
            hospitalRepository.findById(
                hospitalMock.getIdHospital()
            )
        ).thenReturn(Optional.of(hospitalMock));

        when(
            userRepository.updatePassword(hospitalMock.getUser().getIdUser(), newPass.getPassword())
        ).thenReturn(1);

        ResponseEntity<Void> resp = controller.updatePassword(hospitalMock.getIdHospital(), newPass);

        assertEquals(OK, resp.getStatusCode());
    }

    @Test
    @DisplayName("doLogin() - Sem corpo e deve retornar 404 - Not Found")
    void doLoginScenario1() {
        Donator hospitalMock = mock(Donator.class, RETURNS_DEEP_STUBS);
        when(
            hospitalRepository.findByEmailAndPassword(
                hospitalMock.getUser().getEmail(),
                hospitalMock.getUser().getPassword()
            )
        ).thenReturn(Optional.empty());

        when(
            userRepository.authenticateUser(
                hospitalMock.getUser().getIdUser()
            )
        ).thenReturn(0);

        ResponseEntity<Hospital> resp = controller.doLogin(
            new HospitalLoginRequestDTO(
                hospitalMock.getUser().getEmail(),
                hospitalMock.getUser().getPassword()
            )
        );

        assertEquals(NOT_FOUND, resp.getStatusCode());
        assertNull(resp.getBody());
    }

    @Test
    @DisplayName("doLogin() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void doLoginScenario2() {
        Hospital hospitalMock = mock(Hospital.class, RETURNS_DEEP_STUBS);
        when(
            hospitalRepository.findByEmailAndPassword(
                hospitalMock.getUser().getEmail(),
                hospitalMock.getUser().getPassword()
            )
        ).thenReturn(Optional.of(hospitalMock));

        when(
            userRepository.authenticateUser(
                hospitalMock.getUser().getIdUser()
            )
        ).thenReturn(1);

        ResponseEntity<Hospital> resp = controller.doLogin(
            new HospitalLoginRequestDTO(
                hospitalMock.getUser().getEmail(),
                hospitalMock.getUser().getPassword()
            )
        );

        assertEquals(OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
    }
}