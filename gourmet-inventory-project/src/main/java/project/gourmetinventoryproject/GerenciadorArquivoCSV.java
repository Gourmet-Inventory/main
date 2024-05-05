package project.gourmetinventoryproject;

import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioDetalhesDto;

import java.io.*;
import java.util.*;

public class GerenciadorArquivoCSV {


    public static void gravaArquivoCsvUsuario(List<UsuarioDetalhesDto> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar o arquivo
        try {
            for (int i = 0; i < lista.size(); i++) {

                //Recupere um elemento da lista e formate aqui:
                UsuarioDetalhesDto usuario = lista.get(i);
                saida.format("%s;%s;%s;\n",
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getSenha());
            }
        } catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }


//    public static void leArquivoCsv(String nomeArq) {
//        FileReader arq = null;
//        Scanner entrada = null;
//        Boolean deuRuim = false;
//
//        nomeArq += ".csv";
//
//        // Bloco try-catch para abrir o arquivo
//        try {
//            arq = new FileReader(nomeArq);
//            entrada = new Scanner(arq).useDelimiter(";|\\n");
//        } catch (FileNotFoundException erro) {
//            System.out.println("Arquivo nao encontrado");
//            System.exit(1);
//        }
//
//        // Bloco try-catch para ler o arquivo
//        try {
//            //Leia e formate a saída no console aqui:
//
//            // Cabeçalho
//            System.out.printf("%-4S %-15S %-7S %-5S\n", "id", "nome", "porte", "peso");
//            while (entrada.hasNext()) {
//                //Corpo
//
//                int id = entrada.nextInt();
//                String nome = entrada.next();
//                String porte = entrada.next();
//                double peso = entrada.nextDouble();
//
//                System.out.printf("%04d %-15s %-7S %5.1f\n", id, nome, porte, peso);
//
//                // Para salvar um pet no banco:
////                Pet petSalvar = new Pet(id, nome, porte,peso);
////                petRepository.save(petSalvar);
//            }
//        } catch (NoSuchElementException erro) {
//            System.out.println("Arquivo com problemas");
//            deuRuim = true;
//        } catch (IllegalStateException erro) {
//            System.out.println("Erro na leitura do arquivo");
//            deuRuim = true;
//        } finally {
//            entrada.close();
//            try {
//                arq.close();
//            } catch (IOException erro) {
//                System.out.println("Erro ao fechar o arquivo");
//                deuRuim = true;
//            }
//            if (deuRuim) {
//                System.exit(1);
//            }
//        }
//    }

    public static String downloadArquivoCsv(String nomeArq) {
        File arquivoOrigem = new File(nomeArq + ".csv");

        String diretorioDownloads = System.getProperty("user.home") + "/Downloads/";
        File arquivoDestino = new File(diretorioDownloads + nomeArq + ".csv");

        FileInputStream entrada = null;
        FileOutputStream saida = null;
        byte[] buffer = new byte[1024];
        int length;

        try {
            entrada = new FileInputStream(arquivoOrigem);
            saida = new FileOutputStream(arquivoDestino);
            while ((length = entrada.read(buffer)) > 0) {
                saida.write(buffer, 0, length);
            }
            return "Download concluído com sucesso!";
        } catch (IOException e) {
            return "Erro durante o download do arquivo: " + e.getMessage();
        } finally {
            try {
                if (entrada != null) {
                    entrada.close();
                }
                if (saida != null) {
                    saida.close();
                }
            } catch (IOException e) {
                return "Erro ao fechar os fluxos de entrada/saída: " + e.getMessage();
            }
        }
    }
}
