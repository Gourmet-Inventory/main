package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.domain.*;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;
import project.gourmetinventoryproject.dto.saida.SaidaDTO;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.RelatorioRepository;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.*;

@Service
public class RelatorioService {

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;
    @Autowired
    private PratoService pratoService;
    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;
    @Autowired
    private RelatorioRepository relatorioRepository;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private ModelMapper modelMapper;


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

    public void gerarRelatorio(LocalDate data, SaidaDTO relatorioDTO) {
        System.out.println("Entrou no método gerarRelatorio " + "Data: " + data + " Lista de pratosId: " + relatorioDTO.getIdPratoList());
        List<Prato> listaPratos = new ArrayList<>();

        for (Long idPrato : relatorioDTO.getIdPratoList()){
            Prato prato = pratoService.getPratoById(idPrato);
            if (prato != null){
                listaPratos.add(prato);
            }else{
                throw new IdNotFoundException();
            }
        }

        Queue<Prato> pratos = organizarPratos(listaPratos);
        System.out.println("Pratos organizados: " + pratos);

        Empresa empresa = listaPratos.get(0).getEmpresa();
        Relatorio relatorio = new Relatorio();
        relatorio.setData(data);
        relatorio.setPratoList(listaPratos);
        relatorio.setEmpresa(empresa);
        Double valorBruto = 0.0;

        // Calcular o valor bruto dos pratos
        for (Prato prato : pratos) {
            valorBruto += prato.getPreco();

            // Dar baixa nos ingredientes usados no prato
            if (relatorioDTO.getDescontarEstoque()) {
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
        }

        relatorio.setValorBruto(valorBruto);

        try {
            Relatorio antigo = relatorioRepository.findByDataAndEmpresa(data,empresa);

            if (antigo != null ){
                List<Prato> listPrato = antigo.getPratoList();
                listPrato.addAll(listaPratos);
                antigo.setPratoList(listPrato);
                relatorioRepository.save(antigo);
            }else {
                relatorioRepository.save(relatorio);
                System.out.println("Relatório gerado: " + relatorio);}
        } catch (Exception e) {
            System.err.println("Erro ao salvar o relatório: " + e.getMessage());

        }
    }

    public void deletarRelatorio(Long idRelatorio) {
        Optional<Relatorio> relatorio = relatorioRepository.findById(idRelatorio);
        if (relatorio.isPresent()) {
            relatorioRepository.deleteById(idRelatorio);
        } else {
            throw new RuntimeException("Relatório com ID " + idRelatorio + " não encontrado.");
        }
    }

    public String generateCsvEstoque(Long idEmpresa, int mes) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<EstoqueIngrediente> estoques = estoqueIngredienteRepository.findAllByDtaAvisoMonth(mes,empresa);
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("Nome,Data\n");

        for (EstoqueIngrediente estoque : estoques) {
            stringWriter.append(estoque.getNome()).append(",")
                    .append(estoque.getDtaAviso().toString()).append("\n");
        }
        return stringWriter.toString();
    }
    public String generateCsvTotalMes(Long idEmpresa, int mes) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<Relatorio> relatorios = relatorioRepository.findAllByDataMonth(mes,empresa);
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("Nome Prato,Preço \n");
        Double total = 0.0;
        for (int i = 0; i < relatorios.size(); i++) {
            total += relatorios.get(i).getPratoList().get(i).getPreco();
            stringWriter.append(relatorios.get(i).getPratoList().get(i).getNome()).append(",")
                    .append(relatorios.get(i).getPratoList().get(i).getPreco().toString()).append("\n");
        }
        stringWriter.append("Total,"+ total +" \n");
        return stringWriter.toString();
    }



//    private void exibeRelatorio(Relatorio relatorio) {
//        double somaPreco = 0;
//
//        System.out.println("| PRATO             | PREÇO   |");
//        System.out.println("|-------------------|---------|");
//
//        try {
//            return GerenciadorArquivoCSV.gravaArquivoTxtSaida(data, listaPratos, relatorio);
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//
//        double mediaPreco = pratos.isEmpty() ? 0 : relatorio.getValorBruto() / pratos.size();
//        System.out.println("|-------------------|---------|");
//        System.out.printf("| %-18s | %7.2f |\n", "MÉDIA", mediaPreco);
//        System.out.printf("| %-18s | %7.2f |\n", "SOMA", somaPreco);
//
//    }
}
