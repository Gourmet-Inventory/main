package project.gourmetinventoryproject.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.gourmetinventoryproject.domain.NutritionData;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.controller.EstoqueIngredienteController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

@RestController
public class NutricaoAPIController {

    @Autowired
    private EstoqueIngredienteController estoqueIngredienteController;

    @GetMapping("/consulta-nutricao-api/{idEmpresa}")
    public ResponseEntity<byte[]> fetchNutritionDataFromAPI(@PathVariable Long idEmpresa) throws IOException {
        ResponseEntity<List<EstoqueIngredienteConsultaDto>> responseEntity = estoqueIngredienteController.getAllEstoqueIngredientes(idEmpresa);
        List<EstoqueIngredienteConsultaDto> estoqueIngredientes = responseEntity.getBody();

        if (estoqueIngredientes == null || estoqueIngredientes.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<NutritionData> nutritionDataList = new ArrayList<>();

        for (EstoqueIngredienteConsultaDto estoqueIngrediente : estoqueIngredientes) {
            String query = estoqueIngrediente.getValorMedida() + "g%20" + estoqueIngrediente.getNome().replace(" ", "%20");
            URL url = new URL("https://api.api-ninjas.com/v1/nutrition?query=" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-Api-Key", "HVxQ77fkJrXL5oz4yfNsPw==153XAxgTjQ7zcIyc");

            try (InputStream responseStream = connection.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseStream);

                for (JsonNode node : root) {
                    String name = node.path("name").asText();
                    double calories = node.path("calories").asDouble();
                    double servingSize = node.path("serving_size_g").asDouble();
                    double totalFat = node.path("fat_total_g").asDouble();
                    double saturatedFat = node.path("fat_saturated_g").asDouble();
                    double protein = node.path("protein_g").asDouble();
                    int sodium = node.path("sodium_mg").asInt();
                    int potassium = node.path("potassium_mg").asInt();
                    int cholesterol = node.path("cholesterol_mg").asInt();
                    double totalCarbohydrates = node.path("carbohydrates_total_g").asDouble();
                    double fiber = node.path("fiber_g").asDouble();
                    double sugar = node.path("sugar_g").asDouble();

                    NutritionData nutritionData = new NutritionData(name, calories, servingSize, totalFat, saturatedFat, protein, sodium, potassium, cholesterol, totalCarbohydrates, fiber, sugar);
                    nutritionDataList.add(nutritionData);
                }
            } catch (IOException e) {
                // Tratar exceção adequadamente (logar, continuar ou interromper, dependendo do caso)
                e.printStackTrace();
                continue;
            }
        }

        // Ordenar a lista de dados de nutrição
        nutritionDataList.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        // Criar um arquivo Excel
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Nutrition Data");

            // Cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] header = {"Name", "Calories", "Serving Size", "Total Fat", "Saturated Fat", "Protein", "Sodium", "Potassium", "Cholesterol", "Total Carbohydrates", "Fiber", "Sugar"};
            for (int i = 0; i < header.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
            }

            // Dados
            int rowNum = 1;
            for (NutritionData nutritionData : nutritionDataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nutritionData.getName());
                row.createCell(1).setCellValue(nutritionData.getCalories());
                row.createCell(2).setCellValue(nutritionData.getServingSize());
                row.createCell(3).setCellValue(nutritionData.getTotalFat());
                row.createCell(4).setCellValue(nutritionData.getSaturatedFat());
                row.createCell(5).setCellValue(nutritionData.getProtein());
                row.createCell(6).setCellValue(nutritionData.getSodium());
                row.createCell(7).setCellValue(nutritionData.getPotassium());
                row.createCell(8).setCellValue(nutritionData.getCholesterol());
                row.createCell(9).setCellValue(nutritionData.getTotalCarbohydrates());
                row.createCell(10).setCellValue(nutritionData.getFiber());
                row.createCell(11).setCellValue(nutritionData.getSugar());
            }

            workbook.write(outputStream);
        }

        // Retornar o arquivo diretamente na resposta
        byte[] bytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=nutrition_data.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }
}
