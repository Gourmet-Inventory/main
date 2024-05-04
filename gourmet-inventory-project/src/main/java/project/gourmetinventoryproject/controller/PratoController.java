package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoCriacaoDto;
import project.gourmetinventoryproject.service.PratoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Obter lista com todos pratos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de pratos encontrada"),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há pratos disponíveis"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping
    public ResponseEntity<List<PratoConsultaDto>> getAllPratos() {
        List<Prato> pratos = pratoService.getAllPratos();
        return pratos.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(pratos.stream()
                .map(prato-> mapper.map(prato, PratoConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @Operation(summary = "Buscar prato por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Prato encontrado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PratoConsultaDto> getPratoById(@PathVariable Long id) {
        Prato prato = pratoService.getPratoById(id);
        return new ResponseEntity<>(mapper.map(prato,PratoConsultaDto.class), HttpStatus.OK);
    }
    @Operation(summary = "Criar novo prato", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Prato criado com sucesso"),
            @ApiResponse(responseCode ="409", description = "Prato já existe"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PostMapping
    public ResponseEntity<PratoConsultaDto> createPrato(@RequestBody PratoCriacaoDto pratoDto) {
        var entidade = mapper.map(pratoDto, Prato.class);
        pratoService.createPrato(entidade);
        return new ResponseEntity<>(mapper.map(entidade,PratoConsultaDto.class), HttpStatus.CREATED);
    }
    @Operation(summary = "Atualizar prato por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Prato atualizado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PratoConsultaDto> updatePrato(@PathVariable Long id, @RequestBody PratoCriacaoDto pratoDto) {
        var entidade = mapper.map(pratoDto, Prato.class);
        pratoService.updatePrato(id, entidade);
        return new ResponseEntity<>(mapper.map(entidade, PratoConsultaDto.class), HttpStatus.OK);
    }
    @Operation(summary = "Deletar prato por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Prato deletado com sucesso"),
            @ApiResponse(responseCode ="404", description = "ID não encontrado"),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos"),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida"),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la"),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrato(@PathVariable Long id) {
        pratoService.deletePrato(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}