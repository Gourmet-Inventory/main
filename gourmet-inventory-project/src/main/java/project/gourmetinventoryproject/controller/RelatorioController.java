package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.service.RelatorioService;
import project.gourmetinventoryproject.service.UsuarioService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping("/gerar")
    public ResponseEntity<String> gerarRelatorio(@RequestParam("data") LocalDate data, @RequestBody List<Long> idPratoList) {
        String arquivo = relatorioService.gerarRelatorio(data, idPratoList);

        return arquivo.equals("saida_" + data + ".csv") ? status(200).body(arquivo) : status(404).build();
    }
}
