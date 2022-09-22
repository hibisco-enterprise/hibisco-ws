package enterprise.hibisco.hibiscows.rest.googlemaps;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Maps", url = "https://maps.googleapis.com/maps/api/geocode/")
public interface ClientGoogleMaps {

    @GetMapping("json?address={address}&key={apiKey}")
    AllGoogleMapsResponse getAddress(@RequestParam("address")String address,
                       @RequestParam("apiKey") String apiKey);
}
