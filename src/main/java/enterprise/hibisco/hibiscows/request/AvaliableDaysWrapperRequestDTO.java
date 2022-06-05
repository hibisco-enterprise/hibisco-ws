package enterprise.hibisco.hibiscows.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@JsonRootName("AvaliableDays")
@NoArgsConstructor
public class AvaliableDaysWrapperRequestDTO {

    @JsonProperty("days")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Getter @Setter private List<LocalDateTime> avaliableDays;

    /*
    maneira correta de enviar o json de lista
    {
        "days": [
            "2023-04-16 23:03:28",
            "2023-04-16 23:03:28",
            "2023-04-16 23:03:28",
            "2023-04-16 23:03:28"
        ]
    }
    */

}
