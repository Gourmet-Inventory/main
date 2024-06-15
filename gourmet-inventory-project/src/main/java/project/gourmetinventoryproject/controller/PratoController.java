package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.AlergicosRestricoes;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoCriacaoDto;
import project.gourmetinventoryproject.service.PratoService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Obter lista com todos pratos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Lista de pratos encontrada",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="204", description = "Sem conteúdo - Não há pratos disponíveis",
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
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<PratoConsultaDto>> getAllPratos(@PathVariable Long idEmpresa) {
        List<Prato> pratos = pratoService.getAllPratos(idEmpresa);
        return pratos.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(pratos.stream()
                .map(prato-> mapper.map(prato, PratoConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @Operation(summary = "Buscar prato por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - encontrado com sucesso",
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
    public ResponseEntity<PratoConsultaDto> getPratoById(@PathVariable Long id) {
        Prato prato = pratoService.getPratoById(id);
        return new ResponseEntity<>(mapper.map(prato,PratoConsultaDto.class), HttpStatus.OK);
    }
    @Operation(summary = "Criar novo prato", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201", description = "Criado - Prato criado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"nome\": \"Hamburguer\",\"descricao\": \"Substituir batata por ovo\",\"preco\": 30,\"categoria\": \"lanches\"}")})}),
            @ApiResponse(responseCode ="409", description = "Conflito - Prato já existe",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"nome\": \"Hamburguer\",\"descricao\": \"Substituir batata por ovo\",\"preco\": 30,\"categoria\": \"lanches\"}")})}),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"nome\": \"Hamburguer\",\"descricao\": \"Substituir batata por ovo\",\"preco\": String,\"categoria\": \"lanches\"}")})}),
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
    @PostMapping("/{idEmpresa}")
    public ResponseEntity<Void> createPrato(@RequestBody PratoCriacaoDto pratoDto,@PathVariable Long idEmpresa) {
        pratoService.createPrato(pratoDto,idEmpresa);
        return ResponseEntity.status(200).build();
    }
    @Operation(summary = "Atualizar prato por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Prato atualizado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"nome\": \"Hamburguer\",\"descricao\": \"Substituir batata por ovo ou cebola\",\"preco\": 50,\"categoria\": \"lanches\",\"alergico\": \"GLUTEN\"}")})}),
            @ApiResponse(responseCode ="404", description = "Não encontrado - ID não encontrado",
                    content = {@Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "")})}),
            @ApiResponse(responseCode ="400", description = "Requisição inválida - Parâmetros incorretos",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"nome\": \"Hamburguer\",\"descricao\": \"Substituir batata por ovo\",\"preco\": String,\"categoria\": \"lanches\",\"alergico\": \"GLUTEN\"}")})}),
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
    public ResponseEntity<PratoConsultaDto> updatePrato(@PathVariable Long id, @RequestBody PratoCriacaoDto pratoDto) {
        var entidade = mapper.map(pratoDto, Prato.class);
        pratoService.updatePrato(id, entidade);
        return new ResponseEntity<>(mapper.map(entidade, PratoConsultaDto.class), HttpStatus.OK);
    }
    @Operation(summary = "Deletar prato por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Sucesso - Prato deletado com sucesso",
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
    public ResponseEntity<?> deletePrato(@PathVariable Long id) {
        pratoService.deletePrato(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    @PostMapping("/calculate-ingredient-usage")
//    public Map<Long, Integer> calculateIngredientUsage(@RequestBody List<Long> servedDishesIds) {
//        return pratoService.calculateIngredientUsage(servedDishesIds);
//    }
    
//    @PostMapping("/generate-ingredient-usage-report")
//    public ResponseEntity<int[][]> generateIngredientUsageReport(@RequestBody List<Long> servedDishesIds, @RequestParam int numberOfIngredients) {
//        int[][] report = pratoService.generateIngredientUsageReport(servedDishesIds, numberOfIngredients);
//        return new ResponseEntity<>(report, HttpStatus.OK);
//    }

    @PatchMapping(value = "/foto/{id}",
            consumes = {"image/jpeg", "image/png", "image/webp", "image/gif"})
    public ResponseEntity<Void> updatePratoFoto(@PathVariable Long id, @RequestBody byte[] novaFoto) {
        Prato prato = pratoService.updatePratoFoto(id, novaFoto);

        if ( prato != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/alergicos")
    public List<AlergicosRestricoes> getAlergicos() {
        return Arrays.asList(AlergicosRestricoes.values());
    }

    @PostMapping("/generateReport")
    public ResponseEntity<String> generateReport(
            @RequestBody List<Long> servedDishesIds,
            @RequestParam int numberOfIngredients) {
        String downloadsDir = System.getProperty("user.dir") + "/downloads";
        String filePath = downloadsDir + "/ingredientUsageReport.xlsx";

        // Create downloads directory if it doesn't exist
        File dir = new File(downloadsDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            pratoService.generateExcelReport(servedDishesIds, numberOfIngredients, filePath);
            return ResponseEntity.ok("Relatório gerado com sucesso: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar o relatório: " + e.getMessage());
        }
    }

    @GetMapping("/downloadReport")
    public ResponseEntity<byte[]> downloadReport() {
        String filePath = System.getProperty("user.dir") + "/downloads/ingredientUsageReport.xlsx";
        try {
            File file = new File(filePath);
            InputStream inputStream = new FileInputStream(file);
            byte[] fileContent = inputStream.readAllBytes();
            inputStream.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}