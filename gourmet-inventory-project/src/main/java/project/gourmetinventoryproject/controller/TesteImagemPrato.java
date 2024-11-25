package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.service.PratoService;
import project.gourmetinventoryproject.service.S3Service;

import java.io.IOException;

@RestController
@RequestMapping("/testes-imagens")
public class TesteImagemPrato {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private PratoService pratoService;


    @PostMapping(value = "/imagem-prato/{idPrato}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Long idPrato) {
        try {
            Prato prato = pratoService.getPratoById(idPrato);
            String key = s3Service.uploadFile(file,prato);
            return ResponseEntity.ok("Imagem enviada com sucesso: " + key);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar imagem: " + e.getMessage());
        }
    }

    //UPDATE FOTO PRATO////////
    @PatchMapping(value = "/foto/{idPrato}", consumes = "multipart/form-data")
    public ResponseEntity<String> updatePratoFoto(@PathVariable Long idPrato, @RequestBody MultipartFile file) throws IOException {
        Prato prato = pratoService.getPratoById(idPrato);
        try {
            String key = s3Service.updateFile(prato.getFoto(),file,prato);
            return new ResponseEntity<>("Imagem alterada com sucesso", HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao enviar imagem: " + e.getMessage());
        }
    }

}
