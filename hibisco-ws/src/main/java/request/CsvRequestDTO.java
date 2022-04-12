package request;

import enterprise.hibisco.hibiscows.manager.CsvType;
import lombok.Getter;
import lombok.Setter;

public class CsvRequestDTO {
    @Getter @Setter private Enum<CsvType> type;
    @Getter @Setter private String name;
    @Getter @Setter private String email;
    @Getter @Setter private String phoneNumber;
    @Getter @Setter private String address;
    @Getter @Setter private String neighborhood;
    @Getter @Setter private String city;
    @Getter @Setter private String uf;
    @Getter @Setter private String cep;
    @Getter @Setter private String number;

    public CsvRequestDTO(Enum<CsvType> type,
                         String name,
                         String email,
                         String phoneNumber,
                         String address,
                         String neighborhood,
                         String city,
                         String uf,
                         String cep,
                         String number) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.cep = cep;
        this.number = number;
    }
}
