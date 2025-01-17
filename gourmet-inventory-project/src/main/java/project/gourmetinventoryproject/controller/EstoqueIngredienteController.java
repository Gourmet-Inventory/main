package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado.EstoqueManipuladoAtualizacao;
import project.gourmetinventoryproject.dto.estoqueIngrediente.*;
import project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado.EstoqueIngredienteManipuladoConsultaDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado.EstoqueReceitaManipuladoCriacao;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;


import java.util.List;

@RestController
@RequestMapping("/api/estoque-ingrediente")
public class EstoqueIngredienteController {

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;
    @Autowired
    private ModelMapper mapper;


    @Operation(summary = "Obter lista do estoque de ingredientes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Lista de estoque de ingredientes encontrada",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há estoque de ingredientes disponíveis",
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
                            examples = {@ExampleObject(value = "")})})
    })

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<Object>> getAllEstoqueIngredientes(@PathVariable Long idEmpresa) {
        List<Object> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes(idEmpresa);
        return estoqueIngredientes.isEmpty() ?
                new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(estoqueIngredientes, HttpStatus.OK);
    }
    @Operation(summary = "Buscar estoque de ingredientes por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Estoque de ingredientes encontrado com sucesso",
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
    @GetMapping("/id/{id}")
    public ResponseEntity<EstoqueIngredienteConsultaDto> getEstoqueIngredienteById(@PathVariable Long id) {
        EstoqueIngrediente estoqueIngrediente = estoqueIngredienteService.getEstoqueIngredienteById(id);
        return new ResponseEntity<>(mapper.map(estoqueIngrediente, EstoqueIngredienteConsultaDto.class), HttpStatus.OK);
    }
    @Operation(summary = "Criar novo estoque de ingredientes", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Criado - Estoque de ingredientes criado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"UNIDADE\",\"valorMedida\":200,\"unidade\":2,\"valorTotal\":400,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07\"}")})}),
            @ApiResponse(responseCode ="409", description = "Conflito - Estoque de ingredientes já existe",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"UNIDADE\",\"valorMedida\":200,\"unidade\":2,\"valorTotal\":400,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07\"}")})}),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"Diferente do enum\",\"valorMedida\":\"String\",\"valorTotal\":\"String\",\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07\"}")})}),
            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
                    content = {@Content(mediaType = "",
                            examples = {@ExampleObject(value = "text/plain")})}),
            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
    })

    //Criar novo Estoque ingrediente Industrializado
    @PostMapping("/{idEmpresa}")
    public ResponseEntity<EstoqueIngredienteConsultaDto> createEstoqueIngrediente(@PathVariable Long idEmpresa,@RequestBody @Valid EstoqueIngredienteCriacaoDto estoqueIngrediente) {
        var entidade = mapper.map(estoqueIngrediente, EstoqueIngrediente.class);
        estoqueIngredienteService.createEstoqueIngrediente(entidade,idEmpresa);
        var dtoResposta = mapper.map(entidade, EstoqueIngredienteConsultaDto.class);
        return new ResponseEntity<>(dtoResposta, HttpStatus.CREATED);
    }

    //Criar novo Estoque ingrediente Manipulado
    @PostMapping("/manipulado/{idEmpresa}")
    public ResponseEntity<EstoqueIngredienteManipuladoConsultaDto> createEstoqueIngredienteManipulado(@PathVariable Long idEmpresa, @RequestBody @Valid EstoqueReceitaManipuladoCriacao estoqueIngredienteManipulado) {
        EstoqueIngredienteManipuladoConsultaDto estoqueManipulado = estoqueIngredienteService.createEstoqueIngredienteManipulado(idEmpresa,estoqueIngredienteManipulado);
         return new ResponseEntity<>(estoqueManipulado, HttpStatus.CREATED);
    }


    @Operation(summary = "Atualizar estoque de ingredientes", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Estoque de ingredientes atualizado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"UNIDADE\",\"valorMedida\":200,\"unidade\":2,\"valorTotal\":400,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07T19:58:51.560Z\"}")})}),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"UNIDADE\",\"valorMedida\":200,\"unidade\":2,\"valorTotal\":400,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07T19:58:51.560Z\"}")})}),
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
    public ResponseEntity<EstoqueIngredienteConsultaDto> updateEstoqueIngrediente(@PathVariable Long id, @RequestBody EstoqueIngredienteAtualizacaoDto estoqueIngredienteDto) {
        System.out.println(("Recebida requisição para atualizar estoque com ID: {}" + id));
        EstoqueIngredienteConsultaDto updatedEstoqueIngrediente = estoqueIngredienteService.updateEstoqueIngrediente(id, estoqueIngredienteDto);
        System.out.println(("Entidade atualizada: {}" + updatedEstoqueIngrediente));
        return new ResponseEntity<>(updatedEstoqueIngrediente, HttpStatus.OK);
    }

    @PutMapping("/atualizar-estoque-manipulado/{id}")
    public ResponseEntity<EstoqueIngredienteManipuladoConsultaDto> updateEstoqueIngredienteManipulado(
            @PathVariable Long id,
            @RequestBody EstoqueManipuladoAtualizacao estoqueManipuladoAtualizacao) {
        System.out.println("Recebida requisição para atualizar estoque manipulado com ID: " + id);
        EstoqueIngredienteManipuladoConsultaDto updatedEstoqueIngredienteDto =
                estoqueIngredienteService.updateEstoqueIngredienteManipulado(id, estoqueManipuladoAtualizacao);
        System.out.println("Entidade manipulada atualizada: " + updatedEstoqueIngredienteDto);
        return new ResponseEntity<>(updatedEstoqueIngredienteDto, HttpStatus.OK);
    }


    @Operation(summary = "Deletar estoque de ingredientes", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Estoque de ingredientes deletado com sucesso",
                    content = {@Content(mediaType = "text/plain",
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
    public ResponseEntity<?> deleteEstoqueIngrediente(@PathVariable Long id) {
        estoqueIngredienteService.deleteEstoqueIngrediente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/estoque-select/{id}")
    public ResponseEntity<List<EstoqueIngredientePratosSelectDto>> getEstoqueSelect(@PathVariable Long id){
        return ResponseEntity.status(200).body(estoqueIngredienteService.getEIngredientesSelect(id));
    }
    @GetMapping("/Teste")
    public List<EstoqueIngrediente> getAllEstoqueIngredienteByMonth(Long  idEmpresa,int mes){
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredienteByMonth(idEmpresa,mes);
        return estoqueIngredientes;
    }
}





