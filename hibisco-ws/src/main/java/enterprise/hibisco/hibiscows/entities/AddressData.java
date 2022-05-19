package enterprise.hibisco.hibiscows.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@SuppressWarnings("unused")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_address_data")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddressData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    @Getter @Setter private Long idAddress;

    @NotBlank
    @Getter @Setter private String address;

    @NotBlank
    @Getter @Setter private String neighborhood;

    @NotBlank
    @Getter @Setter private String city;

    @NotBlank
    @Getter @Setter private String uf;

    @NotBlank
    @Getter @Setter private String cep;

    @NotNull
    @Getter @Setter private Integer number;

    @Getter @Setter private Float latitude;

    @Getter @Setter private Float longitude;

    public AddressData(Long idAddress,
                       String address,
                       String neighborhood,
                       String city,
                       String uf,
                       String cep,
                       Integer number) {
        this.idAddress = idAddress;
        this.address = address;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.cep = cep;
        this.number = number;
        this.latitude = Float.NaN;
        this.longitude = Float.NaN;
    }
}