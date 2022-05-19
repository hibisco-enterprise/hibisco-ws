package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.manager.Formatter;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.repositories.UserRepository;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.rest.positionstack.AllPositionStackResponse;
import enterprise.hibisco.hibiscows.rest.positionstack.ClientPositionStack;
import enterprise.hibisco.hibiscows.rest.positionstack.PositionStackResponse;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class AddressDataService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientPositionStack clientPositionStack;

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
        AddressResponseDTO response = new AddressResponseDTO(404, null);
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

    public ResponseEntity<PositionStackResponse> getGeocoordinates(AddressData addressData) {
        PositionStackResponse positionStack;
            String address = Formatter.addressFormatter(addressData);
            System.out.println("Address: " + address);
            AllPositionStackResponse addressResponse;
            try {
                addressResponse = clientPositionStack.getAddress(address);
            } catch (FeignException fe) {
                if (fe.status() == -1) {
                    System.out.println("No response from position stack");
                    return status(SERVICE_UNAVAILABLE).build();
                }
                if (fe.status() >= 400 && fe.status() < 500) {
                    System.out.println("Invalid address");
                    System.out.println(fe.getMessage());
                    return status(fe.status()).build();
                }
                System.out.println("Falha no processamento #chamado");
                return status(INTERNAL_SERVER_ERROR).build();
            }
            positionStack = addressResponse.getData().get(0);

        return ok(positionStack);
    }
}
