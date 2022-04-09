package request;

import enterprise.hibisco.hibiscows.manager.CsvType;
import lombok.Getter;
import lombok.Setter;

public class CsvDTO {
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
}
