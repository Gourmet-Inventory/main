package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.service.RelatorioService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;


    @PostMapping("/gerar")
    public void gerarRelatorio(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, @RequestBody List<Prato> listaPratos) {
        relatorioService.gerarRelatorio(data, listaPratos);
    }
}
