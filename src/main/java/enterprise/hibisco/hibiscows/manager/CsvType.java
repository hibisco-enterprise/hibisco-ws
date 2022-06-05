package enterprise.hibisco.hibiscows.manager;

public enum CsvType {
    Donator("Donator"),Hospital("Hospital");
    private final String valor;
    CsvType(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
