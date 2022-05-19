package enterprise.hibisco.hibiscows.manager;

import enterprise.hibisco.hibiscows.entities.AddressData;
import java.text.Normalizer;

public class Formatter {

    public static String cepFormatter (String cep) {
        cep = cep.replaceAll("-","");
        return cep;
    }

    public static String addressFormatter(AddressData addressData) {

        String addressRawString =
                cepFormatter(addressData.getCep()) + " " +
                addressData.getNeighborhood() + " " +
                addressData.getAddress() + " " +
                addressData.getNumber() + " " +
                addressData.getCity() + " " +
                addressData.getUf();

        return utf8Formatter(addressRawString);
    }

    public static String utf8Formatter(String rawString) {
        return Normalizer.normalize(rawString, Normalizer.Form.NFD).replaceAll(
            "[^\\p{ASCII}]",
            ""
        );
    }
}
