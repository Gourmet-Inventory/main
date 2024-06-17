package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.domain.Relatorio;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;


    public Queue<Prato> organizarPratos(List<Prato> pratos) {
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
        return fila;
    }

    public void gerarRelatorio(LocalDate data, List<Prato> listaPratos) {
        Queue<Prato> pratos = organizarPratos(listaPratos);
        Relatorio relatorio = new Relatorio();
        relatorio.setData(data);
        relatorio.getPratosSaidos().addAll(pratos);

        Double valorBruto = 0.0;
        // Calcular o valor bruto dos pratos
        for (Prato prato : pratos) {
            valorBruto += prato.getPreco();

            // Dar baixa nos ingredientes usados no prato
            for (Ingrediente ingrediente : prato.getReceitaPrato()) {
                EstoqueIngrediente estoque = ingrediente.getEstoqueIngrediente();
                if (estoque != null) {
                    Double quantidadeUsada = Double.parseDouble(ingrediente.getValorMedida());
                    estoque.baixarEstoque(quantidadeUsada);
                    estoqueIngredienteService.updateEstoqueIngrediente(estoque.getIdItem(), estoque);
                }
            }
        }

        relatorio.setValorBruto(valorBruto);
        exibeRelatorio(relatorio);
    }

    private void exibeRelatorio(Relatorio relatorio) {

        System.out.println("| PRATO             | PREÇO   |");
        System.out.println("|-------------------|---------|");

        List<Prato> pratos = relatorio.getPratosSaidos();
        for (Prato prato : pratos) {
            System.out.printf("| %-18s | %7.2f |\n", prato.getNome(), prato.getPreco());
        }

        double mediaPreco = pratos.isEmpty() ? 0 : relatorio.getValorBruto() / pratos.size();
        System.out.println("|-------------------|---------|");
        System.out.printf("| %-18s | %7.2f |\n", "MÉDIA", mediaPreco);
    }
}
