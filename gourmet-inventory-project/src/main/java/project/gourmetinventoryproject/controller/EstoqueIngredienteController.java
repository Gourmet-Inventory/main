package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;


import java.util.List;

@RestController
@RequestMapping("/estoque-ingrediente")
public class EstoqueIngredienteController {

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Operation(summary = "Obter lista do estoque de ingredientes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de estoque de ingredientres encontrada"),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há estoque de ingredientes disponíveis"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping
    public ResponseEntity<List<EstoqueIngrediente>> getAllEstoqueIngredientes() {
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes();
        return estoqueIngredientes.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(estoqueIngredientes, HttpStatus.OK);
    }

    @Operation(summary = "Buscar estoque de ingredientes por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Estoque de ingredientes encontrado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueIngrediente> getEstoqueIngredienteById(@PathVariable Long id) {
        EstoqueIngrediente estoqueIngrediente = estoqueIngredienteService.getEstoqueIngredienteById(id);
        return new ResponseEntity<>(estoqueIngrediente, HttpStatus.OK);
    }

    @Operation(summary = "Criar novo estoque de ingredientes", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Estoque de ingredientes criado com sucesso"),
            @ApiResponse(responseCode ="409", description = "Estoque de ingredientes já existe"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PostMapping
    public ResponseEntity<EstoqueIngrediente> createEstoqueIngrediente(@RequestBody EstoqueIngrediente estoqueIngrediente) {
        EstoqueIngrediente newEstoqueIngrediente = estoqueIngredienteService.createEstoqueIngrediente(estoqueIngrediente);
        return new ResponseEntity<>(newEstoqueIngrediente, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar estoque de ingredientes", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Estoque de ingredientes atualizado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstoqueIngrediente> updateEstoqueIngrediente(@PathVariable Long id, @RequestBody EstoqueIngrediente estoqueIngrediente) {
        EstoqueIngrediente updatedEstoqueIngrediente = estoqueIngredienteService.updateEstoqueIngrediente(id, estoqueIngrediente);
        return new ResponseEntity<>(updatedEstoqueIngrediente, HttpStatus.OK);
    }

    @Operation(summary = "Deletar estoque de ingredientes", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Estoque de ingredientes deletado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstoqueIngrediente(@PathVariable Long id) {
        estoqueIngredienteService.deleteEstoqueIngrediente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

