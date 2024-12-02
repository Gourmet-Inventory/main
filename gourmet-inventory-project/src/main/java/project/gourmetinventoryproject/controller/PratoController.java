package project.gourmetinventoryproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.gourmetinventoryproject.domain.AlergicosRestricoes;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredientePratosSelectDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoCriacaoDto;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;
import project.gourmetinventoryproject.service.PratoService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.io.ByteArrayInputStream;


@RestController
@RequestMapping("/api/pratos")
public class PratoController {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PratoService pratoService;
    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;


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
        List<PratoConsultaDto> pratos = pratoService.getAllPratosImagem(idEmpresa);
        return pratos.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(pratos, HttpStatus.OK);

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
        return new ResponseEntity<>(pratoService.getPratoById(id), HttpStatus.OK);
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
    @PostMapping(value = "/{idEmpresa}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createPrato(
            @PathVariable Long idEmpresa,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam Boolean isBebida,
            @RequestParam Double preco,
            @RequestParam String categoria,
            @RequestParam(value ="alergicosRestricoes" , required = false) String alergicosRestricoes,
            @RequestParam(value = "receita", required = false) String receita,
            @RequestParam(value = "imagem", required = false) MultipartFile foto) throws JsonProcessingException {

        System.out.println("Receita: " + receita);
        System.out.println("isBebida: " + isBebida);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



        List<String> alergicosRestricoesList = new ArrayList<>();
        if (alergicosRestricoes != null && !alergicosRestricoes.trim().isEmpty()) {
            try {
                alergicosRestricoesList = objectMapper.readValue(alergicosRestricoes, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Formato de JSON inválido para alergicosRestricoes.");
            }
        }

        List<IngredienteCriacaoDto> receitaList = new ArrayList<>();
        if (receita != null && !receita.trim().isEmpty()) {
            try {
                receitaList = objectMapper.readValue(receita, new TypeReference<List<IngredienteCriacaoDto>>() {});
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Formato de JSON inválido para a receita.");
            }
        }


        PratoCriacaoDto prato = PratoCriacaoDto.builder()
                .nome(nome)
                .descricao(descricao)
                .preco(preco)
                .categoria(categoria)
                .isBebida(Boolean.valueOf(isBebida))
                .receitaPrato(receitaList)
                .alergicosRestricoes(alergicosRestricoesList)
                .build();

            pratoService.createPrato(prato,idEmpresa,foto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePrato(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String descricao,
            @RequestParam Boolean isBebida,
            @RequestParam Double preco,
            @RequestParam String categoria,
            @RequestParam(value ="alergicosRestricoes" , required = false) String alergicosRestricoes,
            @RequestParam(value = "receita", required = false) String receita) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<String> alergicosRestricoesList = new ArrayList<>();
        if (alergicosRestricoes != null && !alergicosRestricoes.trim().isEmpty()) {
            try {
                alergicosRestricoesList = objectMapper.readValue(alergicosRestricoes, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Formato de JSON inválido para alergicosRestricoes.");
            }
        }

        List<IngredienteCriacaoDto> receitaList = new ArrayList<>();
        if (receita != null && !receita.trim().isEmpty()) {
            try {
                receitaList = objectMapper.readValue(receita, new TypeReference<List<IngredienteCriacaoDto>>() {});
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Formato de JSON inválido para a receita.");
            }
        }
        PratoCriacaoDto prato = PratoCriacaoDto.builder()
                .nome(nome)
                .descricao(descricao)
                .preco(preco)
                .categoria(categoria)
                .alergicosRestricoes(alergicosRestricoesList)
                .receitaPrato(receitaList)
                .isBebida(isBebida)
                .build();

        pratoService.updatePrato(id, prato);
        return ResponseEntity.ok().build();
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

    @GetMapping("/ingredientes/{idEmpresa}")
    public ResponseEntity<List<EstoqueIngredientePratosSelectDto>> ingredientes (@PathVariable Long idEmpresa){
        return ResponseEntity.status(200).body(estoqueIngredienteService.getEIngredientesSelect(idEmpresa));
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

    @PostMapping("/generateReport")
    public ResponseEntity<String> generateReport(
            @RequestBody List<Long> servedDishesIds,
            @RequestParam int numberOfIngredients) {
        String downloadsDir = System.getProperty("user.dir") + "/downloads";
        String filePath = downloadsDir + "/ingredientUsageReport.xlsx";

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