package enterprise.hibisco.hibiscows.manager;

import enterprise.hibisco.hibiscows.entities.AddressData;

public class Formatter {

    public static String cepFormatter (String cep) {
        cep = cep.replaceAll("-","");
        return cep;
    }

    public static String removeSpaces (String unformatted) {
        return unformatted.replaceAll(" ", "%20");
    }

    public static String addressFormatter(AddressData addressData) {
        return removeSpaces(cepFormatter(addressData.getCep()) + " " + addressData.getNeighborhood() +
                " " + addressData.getAddress() + " " + addressData.getNumber() + ", "
                + addressData.getCity() + " " + addressData.getUf());
    }
}
