package project.gourmetinventoryproject.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;
import project.gourmetinventoryproject.dto.receita.ReceitaCriacaoDto;
import project.gourmetinventoryproject.service.ReceitaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Obter lista de receitas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Lista de receitas encontrada",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há receitas disponíveis",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
    })
    @GetMapping
    public ResponseEntity<List<ReceitaConsultaDto>> getAllReceitas() {
        List<Receita> receitas = receitaService.getAllReceitas();
        return receitas.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(receitas.stream()
                .map(receita-> mapper.map(receita, ReceitaConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Operation(summary = "Buscar receita por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Lista de receitas encontrada",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReceitaConsultaDto> getReceitaById(@PathVariable Long id) {
        Receita receita = receitaService.getReceitaById(id);
        return new ResponseEntity<>(mapper.map(receita,ReceitaConsultaDto.class), HttpStatus.OK);
    }

    @Operation(summary = "Criar nova receita", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Criado - Receita criada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="409", description = "Conflito - Receita já existe",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
    })
    @PostMapping
    public ResponseEntity<ReceitaConsultaDto> createReceita(@RequestBody @Valid ReceitaCriacaoDto receitaDto) {
        var entidade = mapper.map(receitaDto, Receita.class);
        receitaService.createReceita(entidade);
        return new ResponseEntity<>(mapper.map(entidade, ReceitaConsultaDto.class), HttpStatus.CREATED);
    }
    @Operation(summary = "Atualizar receita por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Receita atualizada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReceitaConsultaDto> updateReceita(@PathVariable Long id, @RequestBody ReceitaCriacaoDto receitaDto) {
        var entidade = mapper.map(receitaDto, Receita.class);
        receitaService.updateReceita(id, entidade);
        return new ResponseEntity<>(mapper.map(entidade, ReceitaConsultaDto.class), HttpStatus.OK);
    }
    @Operation(summary = "Deletar receita por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Receita deletada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReceita(@PathVariable Long id) {
        receitaService.deleteReceita(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

