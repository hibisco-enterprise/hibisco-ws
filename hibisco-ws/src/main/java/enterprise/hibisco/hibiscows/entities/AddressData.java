package enterprise.hibisco.hibiscows.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "tb_address_data")
@NoArgsConstructor
public class AddressData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    @Getter @Setter private Long idAddress;

    @NotBlank @NotNull
    @Getter @Setter private String address;

    @NotBlank @NotNull
    @Getter @Setter private String neighborhood;

    @NotBlank @NotNull
    @Getter @Setter private String city;

    @NotBlank @NotNull @Max(2)
    @Getter @Setter private String uf;

    @NotBlank @NotNull @Min(8) @Max(9)
    @Getter @Setter private String cep;

    @NotNull @Max(6)
    @Getter @Setter private Integer number;

    public AddressData(String address,
                       String neighborhood,
                       String city,
                       String uf,
                       String cep,
                       Integer number) {
        this.address = address;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.cep = cep;
        this.number = number;
    }
}