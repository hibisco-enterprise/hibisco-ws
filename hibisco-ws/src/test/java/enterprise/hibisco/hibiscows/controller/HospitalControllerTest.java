package enterprise.hibisco.hibiscows.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HospitalControllerTest {

    @Test
    @DisplayName("getHospitals() - Sem corpo e deve retornar 204 - No Content")
    void getHospitalsScenario1() {
    }

    @Test
    @DisplayName("getHospitals() - Com corpo (2 registros) e deve retornar 200 - Ok")
    void getHospitalsScenario2() {
    }

    @Test
    @DisplayName("getHospitalById() - Sem corpo e deve retornar 404 - Not Found")
    void getHospitalByIdScenario1() {
    }

    @Test
    @DisplayName("getHospitalById() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void getHospitalByIdScenario2() {
    }

    @Test
    @DisplayName("getDonatorByCnpj() - Sem corpo e deve retornar 404 - Not Found")
    void getDonatorByCnpjScenario1() {
    }

    @Test
    @DisplayName("getDonatorByCnpj() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void getDonatorByCnpjScenario2() {
    }

    @Test
    @DisplayName("updatePassword() - Sem corpo e deve retornar 404 - Not Found")
    void updatePasswordScenario1() {
    }

    @Test
    @DisplayName("updatePassword() - Sem corpo e deve retornar 200 - Ok")
    void updatePasswordScenario2() {
    }

    @Test
    @DisplayName("doLogin() - Sem corpo e deve retornar 404 - Not Found")
    void doLoginScenario1() {
    }

    @Test
    @DisplayName("doLogin() - Com corpo (1 registro) e deve retornar 200 - Ok")
    void doLoginScenario2() {
    }
}