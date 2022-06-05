package enterprise.hibisco.hibiscows.manager;

import enterprise.hibisco.hibiscows.entities.AddressData;
import java.text.Normalizer;

public class Formatter {

    public static String cepFormatter (String cep) throws IllegalStateException {
        if (cep.contains("-")) {
            cep = cep.replaceAll("-","");
            return cep;
        }
        throw new IllegalArgumentException(
            "CEP invalido, verifique a formatação e tente novamente"
        );
    }

    public static String removeSpaces(String addressWithSpaces) {
        if (addressWithSpaces.contains(" ")) {
            addressWithSpaces = addressWithSpaces.replaceAll(" ","%20");
            return addressWithSpaces;
        } else {
            return addressWithSpaces;
        }
    }

    public static String addressFormatter(AddressData addressData) {

        String addressRawString =
                cepFormatter(addressData.getCep()) + " " +
                addressData.getNeighborhood() + " " +
                addressData.getAddress() + " " +
                addressData.getNumber() + " " +
                addressData.getCity() + " " +
                addressData.getUf();

        return utf8Formatter(removeSpaces(addressRawString));
    }

    public static String utf8Formatter(String rawString) {
        return Normalizer.normalize(rawString, Normalizer.Form.NFD).replaceAll(
            "[^\\p{ASCII}]",
            ""
        );
    }
}
