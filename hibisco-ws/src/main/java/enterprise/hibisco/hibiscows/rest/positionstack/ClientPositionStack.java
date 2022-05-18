package enterprise.hibisco.hibiscows.rest.positionstack;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "positionstack", url = "http://api.positionstack.com/v1/forward?access_key=172bd50ab9e7fbf7f05e2565c08c5aa7" )
public interface ClientPositionStack {
    // http://api.positionstack.com/v1/forward?access_key=172bd50ab9e7fbf7f05e2565c08c5aa7&query=1600%20Pennsylvania%20Ave%20NW,%20Washington%20DC
    // 08595370 Parque Macedo Rua Berlim 91 A, Itaquaquecetuba SP
    @GetMapping("&query={address}")
    AllPositionStackResponse getAddress(@PathVariable String address);
}