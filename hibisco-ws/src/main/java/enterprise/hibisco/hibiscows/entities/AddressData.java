package enterprise.hibisco.hibiscows.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@SuppressWarnings("unused")
@Entity
@NoArgsConstructor
@Table(name = "tb_address_data")
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

    @OneToOne(mappedBy = "address")
    @NotBlank
    @Getter @Setter private User user;

    public AddressData(String address,
                       String cep,
                       String city,
                       String neighborhood,
                       Integer number,
                       String uf) {
        this.address = address;
        this.cep = cep;
        this.city = city;
        this.neighborhood = neighborhood;
        this.number = number;
        this.uf = uf;
    }
}