package enterprise.hibisco.hibiscows.manager;

import enterprise.hibisco.hibiscows.entities.AddressData;
import java.text.Normalizer;

public class Formatter {

    public static String cepFormatter (String cep) throws IllegalStateException {
        if (cep.contains("-")) {
            cep = cep.replaceAll("-", "");
            return cep;
        }
        throw new IllegalArgumentException(
            "CEP invalido, verifique a formatação e tente novamente"
        );
    }

    public static String addressFormatter(AddressData addressData) {

        String addressRawString =
            addressData.getAddress() + ", " +
            addressData.getNumber() + " " +
            addressData.getNeighborhood() + " " +
            addressData.getCity() + " " +
            cepFormatter(addressData.getCep()) + " ";

        return utf8Formatter(addressRawString);
    }

    public static String utf8Formatter(String rawString) {
        return Normalizer.normalize(rawString, Normalizer.Form.NFD).replaceAll(
            "[^\\p{ASCII}]",
            ""
        );
    }
}
