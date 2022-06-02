package enterprise.hibisco.hibiscows.service;

import com.google.gson.Gson;
import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.manager.Formatter;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.UserRepository;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.rest.mapbox.AllMapBoxResponse;
import enterprise.hibisco.hibiscows.rest.mapbox.ClientMapBox;
import enterprise.hibisco.hibiscows.rest.mapbox.LatLongDTO;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class AddressDataService {
    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);

    @Autowired
    private AddressRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientMapBox clientMapBox;

    public AddressDataService() {
    }

    public AddressResponseDTO getAddressById(Long idAddress) {
        AddressResponseDTO response = new AddressResponseDTO(404, null);
        if (repository.existsById(idAddress)) {
            response.setStatusCode(200);
            response.setAddressData(repository.findById(idAddress));
            return response;
        }
        return response;
    }

    public AddressResponseDTO updateAddress(Long idAddress, AddressData address) {
        Optional<AddressData> findAddress = repository.findById(idAddress);
        AddressResponseDTO response = new AddressResponseDTO(404,   null);
        ModelMapper mapper = new ModelMapper();
        AddressData newAddress = new AddressData();

        if (findAddress.isPresent()) {

            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(address, newAddress);

            newAddress.setIdAddress(idAddress);
            repository.save(newAddress);

            response.setStatusCode(200);
            response.setAddressData(Optional.of(newAddress));
            return response;
        }
        return response;
    }

    public ResponseEntity<LatLongDTO> getGeocoordinates(AddressData addressData) {
        List<Double> center;
        String address = Formatter.addressFormatter(addressData);
        AllMapBoxResponse mapBoxResponse;

        try {
            mapBoxResponse = clientMapBox.getAddress(address);
            System.out.println(mapBoxResponse.toString());
            }
        catch (FeignException fe) {
            if (fe.status() == -1) {
                logger.error("No response from mapbox");
                return status(SERVICE_UNAVAILABLE).build();
            }
            if (fe.status() >= 400 && fe.status() < 500) {
                System.out.println("Invalid address");
                logger.error(fe.toString());
                return status(fe.status()).build();
            }
            System.out.println("Falha no processamento #chamado");
            logger.error(fe.toString());
            return status(INTERNAL_SERVER_ERROR).build();
            }

        center = mapBoxResponse.getFeatures().get(0).getCenter();

        return ok(
            new LatLongDTO(center.get(0), center.get(1))
        );
    }
}
