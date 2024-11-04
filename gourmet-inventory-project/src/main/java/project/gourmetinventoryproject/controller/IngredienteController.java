package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;
import project.gourmetinventoryproject.service.IngredienteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Obter lista do estoque de ingredientes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Lista de ingredientes encontrada",
                content = {@Content(mediaType = "application/json",
                        examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há ingredientes disponíveis",
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
    public ResponseEntity<List<IngredienteConsultaDto>> getAllIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.getAllIngredientes();
        return ingredientes.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(ingredientes.stream()
                .map(ingrediente-> mapper.map(ingrediente, IngredienteConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }


//    @Operation(summary = "Criar novo ingrediente", method = "POST")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode ="201", description = "Criado - Ingrediente criado com sucesso",
//                    content = {@Content(mediaType = "application/json",
//                            examples = {@ExampleObject(value = "{\"estoqueIngrediente\": {\"idItem\":\"1\",\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"UNIDADE\",\"valorMedida\":200,\"unidade\":2,\"valorTotal\":400,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07T19:58:51.560Z\"},\"tipoMedida\": \"GRAMAS\",\"valorMedida\": \"200\"}")})}),
//            @ApiResponse(responseCode ="409", description = "Conflito - Ingrediente já existe",
//                    content = {@Content(mediaType = "application/json",
//                            examples = {@ExampleObject(value = "{\"estoqueIngrediente\": {\"idItem\":\"1\",\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"UNIDADE\",\"valorMedida\":200,\"unidade\":2,\"valorTotal\":400,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07T19:58:51.560Z\"},\"tipoMedida\": \"GRAMAS\",\"valorMedida\": \"200\"}")})}),
//            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
//                    content = {@Content(mediaType = "text/plain",
//                            examples = {@ExampleObject(value = "{\"estoqueIngrediente\": {\"idItem\":\"1\",\"lote\":\"5A\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"Diferente do enum\",\"valorMedida\":String,\"unidade\":String,\"valorTotal\":String,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07T19:58:51.560Z\"},\"tipoMedida\": \"GRAMAS\",\"valorMedida\": \"200\"}")})}),
//            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
//                    content = {@Content(mediaType = "text/plain",
//                        examples = {@ExampleObject(value = "")})}),
//            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
//                    content = {@Content(mediaType = "text/plain",
//                        examples = {@ExampleObject(value = "")})}),
//            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
//                    content = {@Content(mediaType = "text/plain",
//                        examples = {@ExampleObject(value = "")})}),
//    })
  @PostMapping
 public ResponseEntity<IngredienteConsultaDto> createIngrediente(@RequestBody List<IngredienteCriacaoDto> ingredienteDto) {
        return new ResponseEntity<>(mapper.map(estoqueIngredienteService.createIngrediente(ingredienteDto), IngredienteConsultaDto.class), HttpStatus.CREATED);
    }

//    @Operation(summary = "Atualizar ingrediente", method = "PUT")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode ="200", description = "Sucesso - Ingrediente atualizado com sucesso",
//                    content = {@Content(mediaType = "application/json",
//                            examples = {@ExampleObject(value = "{\"estoqueIngrediente\": {\"idItem\":\"1\",\"lote\":\"5B\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"UNIDADE\",\"valorMedida\":200,\"unidade\":3,\"valorTotal\":600,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07T19:58:51.560Z\"},\"tipoMedida\": \"GRAMAS\",\"valorMedida\": \"200\"}")})}),
//            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado",
//                    content = {@Content(mediaType = "text/plain",
//                            examples = {@ExampleObject(value = "")})}),
//            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
//                    content = {@Content(mediaType = "application/json",
//                            examples = {@ExampleObject(value = "{\"estoqueIngrediente\": {\"idItem\":\"1\",\"lote\":\"5B\",\"nome\":\"Arroz\",\"categoria\":\"Grãos\",\"tipoMedida\":\"Diferente do enum\",\"valorMedida\":String,\"unidade\":String,\"valorTotal\":String,\"localArmazenamento\":\"Estoque\",\"dtaCadastro\":\"2024-05-07\",\"dtaAviso\":\"2024-05-07T19:58:51.560Z\"},\"tipoMedida\": \"GRAMAS\",\"valorMedida\": \"200\"}")})}),
//            @ApiResponse(responseCode ="401", description = "Não autorizado - Autenticação necessária e falhou ou ainda não foi fornecida",
//                    content = {@Content(mediaType = "text/plain",
//                            examples = {@ExampleObject(value = "")})}),
//            @ApiResponse(responseCode ="403", description = "Proibido - O servidor entende a requisição, mas se recusa a autorizá-la",
//                    content = {@Content(mediaType = "text/plain",
//                            examples = {@ExampleObject(value = "")})}),
//            @ApiResponse(responseCode ="500", description = "Erro interno no servidor - Problema ao processar a requisição",
//                    content = {@Content(mediaType = "text/plain",
//                            examples = {@ExampleObject(value = "")})}),
//    })
//    @PutMapping("/{id}")
//    public ResponseEntity<IngredienteConsultaDto> updateIngrediente(@PathVariable Long id, @RequestBody IngredienteCriacaoDto ingredienteDto) {
//        var entidade = mapper.map(ingredienteDto, Ingrediente.class);
//        ingredienteService.updateIngrediente(id, entidade);
//        return new ResponseEntity<>(mapper.map(entidade,IngredienteConsultaDto.class), HttpStatus.OK);
//    }

    @Operation(summary = "Deletar ingrediente", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Ingrediente deletado com sucesso",
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
    public ResponseEntity<?> deleteIngrediente(@PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}