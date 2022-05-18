package enterprise.hibisco.hibiscows.rest.positionstack;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "positionstack", url = "http://api.positionstack.com/v1/forward?access_key=172bd50ab9e7fbf7f05e2565c08c5aa7&")
public interface ClientPositionStack {
    // http://api.positionstack.com/v1/forward?access_key=172bd50ab9e7fbf7f05e2565c08c5aa7&query=01414001%20Cerqueira%20Cesar%20Rua%20Haddock%20Lobo%20595,%20Sao%20Paulo%20SP
    @GetMapping("query={address}")
    AllPositionStackResponse getAddress(@PathVariable String address);
}