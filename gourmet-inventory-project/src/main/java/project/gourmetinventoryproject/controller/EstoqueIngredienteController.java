package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteCriacaoDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estoque-ingrediente")
public class EstoqueIngredienteController {

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Obter lista do estoque de ingredientes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Lista de estoque de ingredientres encontrada"),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há estoque de ingredientes disponíveis"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping
    public ResponseEntity<List<EstoqueIngredienteConsultaDto>> getAllEstoqueIngredientes() {
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes();
        return estoqueIngredientes.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(estoqueIngredientes.stream()
                .map(estoqueIngrediente -> mapper.map(estoqueIngrediente, EstoqueIngredienteConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @Operation(summary = "Buscar estoque de ingredientes por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Estoque de ingredientes encontrado com sucesso"),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueIngredienteConsultaDto> getEstoqueIngredienteById(@PathVariable Long id) {
        EstoqueIngrediente estoqueIngrediente = estoqueIngredienteService.getEstoqueIngredienteById(id);
        return new ResponseEntity<>(mapper.map(estoqueIngrediente, EstoqueIngredienteConsultaDto.class), HttpStatus.OK);
    }
    @Operation(summary = "Criar novo estoque de ingredientes", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Criado - Estoque de ingredientes criado com sucesso"),
            @ApiResponse(responseCode ="409", description = "Conflito - Estoque de ingredientes já existe"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PostMapping
    public ResponseEntity<EstoqueIngredienteConsultaDto> createEstoqueIngrediente(@RequestBody EstoqueIngredienteCriacaoDto estoqueIngrediente) {
        var entidade = mapper.map(estoqueIngrediente, EstoqueIngrediente.class);
        estoqueIngredienteService.createEstoqueIngrediente(entidade);
        var dtoResposta = mapper.map(entidade, EstoqueIngredienteConsultaDto.class);
        return new ResponseEntity<>(dtoResposta, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar estoque de ingredientes", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Estoque de ingredientes atualizado com sucesso"),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstoqueIngredienteConsultaDto> updateEstoqueIngrediente(@PathVariable Long id, @RequestBody EstoqueIngredienteCriacaoDto estoqueIngredienteDto) {
        var entidade = mapper.map(estoqueIngredienteDto, EstoqueIngrediente.class);
        estoqueIngredienteService.updateEstoqueIngrediente(id, entidade );
        return new ResponseEntity<>(mapper.map(entidade, EstoqueIngredienteConsultaDto.class), HttpStatus.OK);
    }

    @Operation(summary = "Deletar estoque de ingredientes", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Estoque de ingredientes deletado com sucesso"),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstoqueIngrediente(@PathVariable Long id) {
        estoqueIngredienteService.deleteEstoqueIngrediente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

