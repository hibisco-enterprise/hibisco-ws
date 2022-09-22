package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.manager.Formatter;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.rest.googlemaps.AllGoogleMapsResponse;
import enterprise.hibisco.hibiscows.rest.googlemaps.ClientGoogleMaps;
import enterprise.hibisco.hibiscows.rest.mapbox.LatLongDTO;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class AddressDataService {
    private static final Logger logger = LoggerFactory.getLogger(AddressDataService.class);

    @Autowired
    private AddressRepository repository;

    @Autowired
    private ClientGoogleMaps clientGoogleMaps;

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
        List<Double> center = new ArrayList<>();
        String address = Formatter.addressFormatter(addressData);
        logger.info("ENDEREÇO TOP AQUI: {}", address);
        AllGoogleMapsResponse allGoogleMapsResponse = new AllGoogleMapsResponse();
        String apiKey = System.getenv("KEY_GOOGLE_MAPS");

        try {
            allGoogleMapsResponse = clientGoogleMaps.getAddress(address, apiKey);
            logger.info(allGoogleMapsResponse.toString());
        } catch (FeignException fe) {
            if (fe.status() == -1) {
                logger.error("No response from google maps");
                return status(SERVICE_UNAVAILABLE).build();
            }
            if (fe.status() >= 400 && fe.status() < 500) {
                logger.error("Invalid address, look: {}", address);
                logger.error(fe.toString());
                return status(fe.status()).build();
            }
            logger.error("Falha no processamento #chamado");
            logger.error(fe.toString());
            return status(INTERNAL_SERVER_ERROR).build();
            }


        center.add(allGoogleMapsResponse.getResults()[0].getGeometry().getLocation().getLng());
        center.add(allGoogleMapsResponse.getResults()[0].getGeometry().getLocation().getLat());

        return ok(
            new LatLongDTO(center.get(0), center.get(1))
        );
    }
}
