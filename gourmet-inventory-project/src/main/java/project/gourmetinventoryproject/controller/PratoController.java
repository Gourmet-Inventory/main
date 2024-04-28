package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.service.PratoService;

import java.util.List;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @Operation(summary = "Obter lista com todos pratos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de pratos encontrada"),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há pratos disponíveis")
    })
    @GetMapping
    public ResponseEntity<List<Prato>> getAllPratos() {
        List<Prato> pratos = pratoService.getAllPratos();
        return pratos.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(pratos, HttpStatus.OK);
    }
    @Operation(summary = "Buscar prato por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Prato encontrado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Prato> getPratoById(@PathVariable Long id) {
        Prato prato = pratoService.getPratoById(id);
        return new ResponseEntity<>(prato, HttpStatus.OK);
    }
    @Operation(summary = "Criar novo prato", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Prato criado com sucesso"),
            @ApiResponse(responseCode ="409", description = "Prato já existe"),
    })
    @PostMapping
    public ResponseEntity<Prato> createPrato(@RequestBody Prato prato) {
        Prato newPrato = pratoService.createPrato(prato);
        return new ResponseEntity<>(newPrato, HttpStatus.CREATED);
    }
    @Operation(summary = "Atualizar prato por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Prato atualizado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Prato> updatePrato(@PathVariable Long id, @RequestBody Prato prato) {
        Prato updatedPrato = pratoService.updatePrato(id, prato);
        if (updatedPrato != null) {
            return new ResponseEntity<>(updatedPrato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Deletar prato por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Prato deletado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrato(@PathVariable Long id) {
        pratoService.deletePrato(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}