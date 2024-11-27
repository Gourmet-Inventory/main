package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Relatorio;
import project.gourmetinventoryproject.dto.saida.SaidaDTO;
import project.gourmetinventoryproject.repository.RelatorioRepository;
import project.gourmetinventoryproject.service.EmpresaService;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;
import project.gourmetinventoryproject.service.RelatorioService;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Autowired
    private RelatorioRepository relatorioRepository;
    @Autowired
    private EmpresaService empresaService;

//    @PostMapping("/gerar/{data}")
//    public ResponseEntity<Void> gerarRelatorio(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate data, @RequestBody SaidaDTO relatorio) {
//        relatorioService.gerarRelatorio(data, relatorio);
//        return ResponseEntity.status(200).build();
//    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<Relatorio>> getAllRelatorios(Long idEmpresa) {
        List<Relatorio> lista = relatorioService.getAllRelatoriosByEmpresa(idEmpresa);
        return ResponseEntity.status(200).body(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarRelatorio(@PathVariable Long id) {
        try {
            relatorioService.deletarRelatorio(id);
            return ResponseEntity.ok("Relatório deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/RelatorioEstoque/{idEmpresa}")
    public ResponseEntity<byte[]> downloadCsvEstoqueMes(@PathVariable Long idEmpresa, @RequestParam int mes) {
        String csvContent = relatorioService.generateCsvEstoque(idEmpresa, mes);
        byte[] bytes = csvContent.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estoques.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/RelatorioSaidaMes/{idEmpresa}")
    public ResponseEntity<byte[]> downloadCsvSaidaMes(@PathVariable Long idEmpresa, @RequestParam int mes) {
        String csvContent = relatorioService.generateCsvTotalMes(idEmpresa, mes);
        byte[] bytes = csvContent.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estoques.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

//    @GetMapping("/ingredientes/{idRelatorio}")
//    public ResponseEntity<byte[]> downloadCsvIngredientes(@PathVariable Long idRelatorio) {
//        try {
//            String csvContent = relatorioService.generateCsvIngredientes(idRelatorio);
//            byte[] bytes = csvContent.getBytes();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ingredientes.csv");
//            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
//            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
