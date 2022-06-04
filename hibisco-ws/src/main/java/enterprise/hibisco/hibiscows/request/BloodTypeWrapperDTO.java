package enterprise.hibisco.hibiscows.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@NoArgsConstructor
public class BloodTypeWrapperDTO {
    @Getter @Setter
    @Range(min = 0, max = (long) 100.0, message = "Insira um número válido entre 0% e 100%")
    private String bloodType;

    @Getter @Setter
    @Length(min = 2, max = 3, message = "Tipo sanguíneo inválido.")
    private Double percentage;
}
