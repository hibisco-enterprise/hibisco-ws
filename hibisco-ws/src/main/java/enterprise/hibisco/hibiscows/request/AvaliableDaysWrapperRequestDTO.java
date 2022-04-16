package enterprise.hibisco.hibiscows.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.util.List;


public class AvaliableDaysWrapperRequestDTO {

    @FutureOrPresent(message = "Data incorreta. Insira uma data presente ou futura")
    @Getter @Setter List<LocalDateTime> avaliableDays;

}
