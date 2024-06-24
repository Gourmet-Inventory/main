package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.domain.Relatorio;

import java.time.LocalDate;
import java.util.*;

@Service
public class RelatorioService {

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Autowired
    private PratoService pratoService;

    public Queue<Prato> organizarPratos(List<Prato> pratos) {
        System.out.println("Entrou no método organizarPratos om lista de pratos: " + pratos);

        Stack<Prato> pilha = new Stack<>();
        Queue<Prato> fila = new LinkedList<>();

        // Inserindo os itens da lista na pilha
        for (Prato prato : pratos) {
            pilha.push(prato);
        }

        // Transferindo os itens da pilha para a fila
        while (!pilha.isEmpty()) {
            fila.add(pilha.pop());
        }

        System.out.println("Pratos organizados em fila: " + fila);
        return fila;
    }

    public String gerarRelatorio(LocalDate data, List<Long> idPratoList) {
        System.out.println("Entrou no método gerarRelatorio " + "Data: " + data + " Lista de pratosId: " + idPratoList);

        List<Prato> listaPratos = new ArrayList<Prato>();

        for (Long id : idPratoList) {
            listaPratos.add(pratoService.getPratoById(id));
        }

        Queue<Prato> pratos = organizarPratos(listaPratos);
        System.out.println("Pratos organizados: " + pratos);

        Relatorio relatorio = new Relatorio();
        relatorio.setData(data);
        relatorio.setPratosSaidos(listaPratos);

        Double valorBruto = 0.0;
        // Calcular o valor bruto dos pratos
        for (Prato prato : pratos) {
            valorBruto += prato.getPreco();

            // Dar baixa nos ingredientes usados no prato
            for (Ingrediente ingrediente : prato.getReceitaPrato()) {
                EstoqueIngrediente estoque = ingrediente.getEstoqueIngrediente();
                if (estoque != null) {
                    Double quantidadeUsada = ingrediente.getValorMedida();
                    estoque.baixarEstoque(quantidadeUsada);
                    estoqueIngredienteService.updateEstoqueIngrediente(estoque.getIdItem(), estoque);
                    estoqueIngredienteService.setValorMedidaIfNegativo(estoque.getIdItem());
                }
            }
        }

        relatorio.setValorBruto(valorBruto);
        
        System.out.println("Relatório gerado: " + relatorio);
//        exibeRelatorio(relatorio);
//        GerenciadorArquivoCSV.gravaArquivoCsvSaida(data, listaPratos, relatorio);
        return downloadFile(data, listaPratos, relatorio);
    }

    public static String downloadFile(LocalDate data, List<Prato> listaPratos, Relatorio relatorio) {
        System.out.println("Entrou no método downloadFile");

        try {
            return GerenciadorArquivoCSV.gravaArquivoTxtSaida(data, listaPratos, relatorio);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    private void exibeRelatorio(Relatorio relatorio) {
//        double somaPreco = 0;
//
//        System.out.println("| PRATO             | PREÇO   |");
//        System.out.println("|-------------------|---------|");
//
//        List<Prato> pratos = relatorio.getPratosSaidos();
//        for (Prato prato : pratos) {
//            System.out.printf("| %-18s | %7.2f |\n", prato.getNome(), prato.getPreco());
//            somaPreco += prato.getPreco();
//        }
//
//        double mediaPreco = pratos.isEmpty() ? 0 : relatorio.getValorBruto() / pratos.size();
//        System.out.println("|-------------------|---------|");
//        System.out.printf("| %-18s | %7.2f |\n", "MÉDIA", mediaPreco);
//        System.out.printf("| %-18s | %7.2f |\n", "SOMA", somaPreco);
//
//    }
}
