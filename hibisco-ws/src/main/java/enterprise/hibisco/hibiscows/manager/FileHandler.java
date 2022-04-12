package enterprise.hibisco.hibiscows.manager;

import request.CsvRequestDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class FileHandler {
    public static void gravaArquivoCsv(ListaObj<CsvRequestDTO> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;
        nomeArq += ".csv";

        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        try {
            for (int i = 0; i < lista.getTamanho(); i++) {
                CsvRequestDTO csv = lista.getElemento(i);
                saida.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;\n", csv.getType(), csv.getName(), csv.getEmail(), csv.getPhoneNumber(),
                csv.getAddress(), csv.getNeighborhood(), csv.getCity(), csv.getUf(), csv.getCep(), csv.getNumber());
            }
        }
        catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        }
        finally {
            saida.close();
            try {
                arq.close();
            }
            catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }
}
