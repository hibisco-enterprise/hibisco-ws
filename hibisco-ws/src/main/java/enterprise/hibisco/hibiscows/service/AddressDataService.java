package enterprise.hibisco.hibiscows.service;

import enterprise.hibisco.hibiscows.entities.AddressData;
import enterprise.hibisco.hibiscows.repositories.AddressRepository;
import enterprise.hibisco.hibiscows.response.AddressResponseDTO;
import enterprise.hibisco.hibiscows.response.LatLongResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddressDataService {

    @Autowired
    private AddressRepository repository;

    public LatLongResponseDTO getHospitalLongLat() {
        return new LatLongResponseDTO(2.0, 2.0);
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

}
