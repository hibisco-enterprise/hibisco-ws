package enterprise.hibisco.hibiscows.manager;

import enterprise.hibisco.hibiscows.controller.HospitalController;
import enterprise.hibisco.hibiscows.entities.BloodStock;
import enterprise.hibisco.hibiscows.entities.Hospital;
import enterprise.hibisco.hibiscows.request.BloodTypeWrapperDTO;
import enterprise.hibisco.hibiscows.request.CsvRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public static List<BloodTypeWrapperDTO> leArquivoTxt(MultipartFile file)  {
        BufferedReader entrada = null;

        String bloodType;
        String documentNumber = "";
        Double percentage;
        String registro, tipoRegistro;
        int contaRegCorpoLido = 0;
        int qtdRegCorpoGravado;

        List<BloodTypeWrapperDTO> bloodStockList = new ArrayList<>();
        try {
            entrada = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            System.out.println(entrada.readLine());
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo: " + erro);
        }

        try {
            registro = entrada.readLine();
            while (registro != null) {
                tipoRegistro = registro.substring(0,2);
                if (tipoRegistro.equals("00")) {
                    System.out.println("É um registro de header");
                    System.out.println("Tipo de arquivo: " + registro.substring(2,9));
                    System.out.println("Data e hora da gravação: " + registro.substring(9,28));
                    System.out.println("Versão do documento: " + registro.substring(28, 30));
                }
                else if (tipoRegistro.equals("01")) {
                    System.out.println("É um registro de trailer");
                    qtdRegCorpoGravado = Integer.parseInt(registro.substring(2,6));
                    if (contaRegCorpoLido == qtdRegCorpoGravado) {
                        System.out.println("Quantidade de registros lidos é compatível " +
                                "com a quantidade de registros gravados");
                    }
                    else {
                        System.out.println("Quantidade de registros lidos não é compatível " +
                                "com a quantidade de registros gravados");
                    }
                }
                else if (tipoRegistro.equals("02")) {
                    System.out.println("É um registro de corpo");
                    bloodType = registro.substring(2,5).trim();
                    percentage = Double.valueOf(registro.substring(5,11).replace(',','.'));
                    contaRegCorpoLido++;

                    bloodStockList.add(new BloodTypeWrapperDTO(bloodType, percentage));
                } else if (tipoRegistro.equals("03")) {
                    System.out.println("É um registro de corpo");
                    documentNumber = registro.substring(102,116).trim();
                    contaRegCorpoLido++;
                }
                else {
                    System.out.println("Tipo de registro inválido!");
                }
                registro = entrada.readLine();
            }
            entrada.close();
        }
        catch (IOException erro) {
            System.out.println("Erro ao ler o arquivo: " + erro);
        }
        for (BloodTypeWrapperDTO b: bloodStockList) {
            b.setDocumentNumber(documentNumber);
        }

        return bloodStockList;
    }
}
