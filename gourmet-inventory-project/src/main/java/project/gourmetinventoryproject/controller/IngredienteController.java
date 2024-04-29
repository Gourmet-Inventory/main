package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.service.IngredienteService;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;


    @Operation(description = "Obter lista de ingredientes",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de ingredientres encontrada"),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há ingredientes disponíveis"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping
    public ResponseEntity<List<Ingrediente>> getAllIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.getAllIngredientes();
        return ingredientes.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(ingredientes, HttpStatus.OK);
    }

    @Operation(summary = "Buscar ingredientes por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Ingrediente encontrado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> getIngredienteById(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteService.getIngredienteById(id);
        return new ResponseEntity<>(ingrediente, HttpStatus.OK);
    }

    @Operation(summary = "Criar novo ingrediente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Ingrediente criado com sucesso"),
            @ApiResponse(responseCode ="409", description = "Ingrediente já existe"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PostMapping
    public ResponseEntity<Ingrediente> createIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente newIngrediente = ingredienteService.createIngrediente(ingrediente);
        return new ResponseEntity<>(newIngrediente, HttpStatus.CREATED);

    }

    @Operation(summary = "Atualizar ingrediente", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Ingrediente atualizado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> updateIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        Ingrediente updatedIngrediente = ingredienteService.updateIngrediente(id, ingrediente);
        return new ResponseEntity<>(updatedIngrediente, HttpStatus.OK);
    }

    @Operation(summary = "Deletar ingrediente", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Ingrediente deletado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngrediente(@PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}