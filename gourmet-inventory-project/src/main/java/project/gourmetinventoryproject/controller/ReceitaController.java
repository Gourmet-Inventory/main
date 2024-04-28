package project.gourmetinventoryproject.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.service.ReceitaService;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @Operation(summary = "Obter lista de receitas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de receitas encontrada"),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há receitas disponíveis")
    })
    @GetMapping
    public ResponseEntity<List<Receita>> getAllReceitas() {
        List<Receita> receitas = receitaService.getAllReceitas();
        return receitas.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(receitas, HttpStatus.OK);
    }

    @Operation(summary = "Buscar receita por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de receitas encontrada"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Receita> getReceitaById(@PathVariable Long id) {
        Receita receita = receitaService.getReceitaById(id);
        return new ResponseEntity<>(receita, HttpStatus.OK);
    }

    @Operation(summary = "Criar nova receita", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Receita criada com sucesso"),
            @ApiResponse(responseCode ="409", description = "Receita já existe"),
    })
    @PostMapping
    public ResponseEntity<Receita> createReceita(@RequestBody Receita receita) {
        Receita newReceita = receitaService.createReceita(receita);
        return new ResponseEntity<>(newReceita, HttpStatus.CREATED);
    }


    @Operation(summary = "Atualizar receita por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Receita atualizada com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Receita> updateReceita(@PathVariable Long id, @RequestBody Receita receita) {
        Receita updatedReceita = receitaService.updateReceita(id, receita);
        return new ResponseEntity<>(updatedReceita, HttpStatus.OK);
    }

    @Operation(summary = "Deletar receita por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Receita deletada com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReceita(@PathVariable Long id) {
        receitaService.deleteReceita(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

