package enterprise.hibisco.hibiscows.rest.mapbox;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "mapbox", url = "https://api.mapbox.com/geocoding/v5/mapbox.places/")
public interface ClientMapBox {
    // https://api.mapbox.com/geocoding/v5/mapbox.places/
    // 27%20rua%20vila%20nova%20santo%20eduardo%20embu%20das%20artes%20sao%20paulo%2006823530
    // .json?country=br&limit=1&types=address&language=pt
    // &access_token=pk.eyJ1IjoiaGliaXNjb2VudGVycHJpc2UiLCJhIjoiY2wyNTd1Nmd0MTVoODNjcDc5OXBmODYxMCJ9.PQo8LI7tQYbPRTdWbCJXSw
    @GetMapping("{address}.json?country=br&limit=1&types=address&language=pt&access_token=pk.eyJ1IjoiaGliaXNjb2VudGVycHJpc2UiLCJhIjoiY2wyNTd1Nmd0MTVoODNjcDc5OXBmODYxMCJ9.PQo8LI7tQYbPRTdWbCJXSw")
    AllMapBoxResponse getAddress(@PathVariable String address);
}
