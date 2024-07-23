package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Relatorio;
import project.gourmetinventoryproject.dto.saida.SaidaDTO;
import project.gourmetinventoryproject.repository.RelatorioRepository;
import project.gourmetinventoryproject.service.RelatorioService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private RelatorioRepository relatorioRepository;

    @PostMapping("/gerar/{data}")
        public ResponseEntity<Void> gerarRelatorio(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate data, @RequestBody SaidaDTO relatorio) {
            relatorioService.gerarRelatorio(data,relatorio);
            return ResponseEntity.status(200).build();
        }

        @GetMapping
        public ResponseEntity<List<Relatorio>> getAllRelatorios(){
            List<Relatorio> lista = relatorioRepository.findAll();
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

    @GetMapping("/{idRelatorio}")
        public ResponseEntity<byte[]> gerarRelatorioInd(@PathVariable Long idRelatorio){
            relatorioService.gerarRelatorioIndividual(idRelatorio);
            return ResponseEntity.status(200).build();
        }

//        @GetMapping("/alertas")
//        public ResponseEntity<byte[]> gerarRelatorioAlertas(){
//            relatorioService.gerarRelatorioAlertas()
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(excelBytes);
//        }
//
//    @GetMapping("/saidos")
//    public ResponseEntity<byte[]> gerarRelatorioSaidos(){
//        relatorioService.gerarRelatorioSaidos()
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(excelBytes);
//    }

}
