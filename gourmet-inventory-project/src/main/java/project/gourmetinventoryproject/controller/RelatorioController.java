package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.service.RelatorioService;
import project.gourmetinventoryproject.service.UsuarioService;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping("/gerar/{data}")
    public ResponseEntity<File> gerarRelatorio(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate data, @RequestBody List<Long> idPratoList) {
        String relatorioGerado = relatorioService.gerarRelatorio(data, idPratoList);
        System.out.println(relatorioGerado);
        File arquivoGerado = GerenciadorArquivoCSV.downloadArquivoTxt(relatorioGerado);
        System.out.println(arquivoGerado);

        return ResponseEntity.status(200).body(arquivoGerado);
    }

//    @GetMapping("txtTeste/{nome}")
//    //public ResponseEntity<String> gerarTxt(@PathVariable String nome) {
//        GerenciadorArquivoCSV.gravaArquivoTxtTeste(nome);
//
//        String arquivoGerado = GerenciadorArquivoCSV.downloadArquivoTxt(nome);
//        System.out.println(arquivoGerado);
//
//        return arquivoGerado.equals("Download concluído com sucesso!") ? status(200).body(arquivoGerado) : status(404).build();
//    }

}
