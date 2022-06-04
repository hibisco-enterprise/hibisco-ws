package enterprise.hibisco.hibiscows.manager;

import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.request.CsvRequestDTO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;

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

    public static void gravaRegistro(String registro, String nomeArq) {
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter(nomeArq,true));
        }
        catch(IOException erro) {
            System.out.println("Erro na abertura do arquivo: " + erro);
        }

        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch(IOException erro) {
            System.out.println("Erro na gravação do arquivo: " + erro);
        }
    }

    public static String gravaArquivoTxt(List<Hospital> lista, String nomeArq) {
        int contaRegistroCorpo = 0;

        String registro = "";
        String header = "00USUARIOS";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";
        registro += header + "\n";
        gravaRegistro(header, nomeArq);

        String corpo;
        for (Hospital a : lista) {
            corpo = "02";
            corpo += String.format("%02d", a.getIdHospital());
            corpo += String.format("%-50.50s", a.getUser().getName());
            corpo += String.format("%-50.50s", a.getUser().getEmail());
            corpo += String.format("%-13.13s", a.getUser().getPhone());
            corpo += String.format("%-9.9s", a.getUser().getAddress().getCep());
            corpo += String.format("%-40.40s", a.getUser().getAddress().getAddress());
            corpo += String.format("%05d", a.getUser().getAddress().getNumber());
            corpo += String.format("%-40.40s", a.getUser().getAddress().getNeighborhood());
            corpo += String.format("%-30.30s", a.getUser().getAddress().getCity());
            corpo += String.format("%-2.2s", a.getUser().getAddress().getUf());
            gravaRegistro(corpo, nomeArq);
            registro += corpo + "\n";
            contaRegistroCorpo++;
        }

        String trailer = "03";
        trailer += String.format("%010d", contaRegistroCorpo);
        registro += trailer + "\n";
        gravaRegistro(trailer, nomeArq);

        return registro;
    }
}
