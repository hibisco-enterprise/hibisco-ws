package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.responses.HospitalResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    private List<User> hospitals;

    public HospitalService() {
        this.hospitals = new ArrayList<>();
    }

    public ResponseEntity doRegister(HospitalResponseDTO hospital) {
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity getHospitals() {
        return ResponseEntity.status(hospitals.isEmpty() ? 204 : 200).body(hospitals);
    }

    public ResponseEntity doLogin(HospitalResponseDTO hospital) {

        return ResponseEntity.status(401).build();
    }

    public ResponseEntity doLogoff(Long idUser) {

        return ResponseEntity.status(404).build();
    }
}
