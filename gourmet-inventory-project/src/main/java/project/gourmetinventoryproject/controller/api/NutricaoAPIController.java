package project.gourmetinventoryproject.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.gourmetinventoryproject.domain.NutritionData;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.controller.EstoqueIngredienteController;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NutricaoAPIController {

    @Autowired
    private EstoqueIngredienteController estoqueIngredienteController;

    @GetMapping("/consulta-nutricao-api")
    public List<NutritionData> fetchNutritionDataFromAPI() throws IOException {
        ResponseEntity<List<EstoqueIngredienteConsultaDto>> responseEntity = estoqueIngredienteController.getAllEstoqueIngredientes();
        List<EstoqueIngredienteConsultaDto> estoqueIngredientes = responseEntity.getBody();

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
            }
        }

        // Ordenar a lista de dados de nutrição
        mergeSort(nutritionDataList);

        // Salvar a lista ordenada em um arquivo CSV
        String[] header = {"Name", "Calories", "Serving Size", "Total Fat", "Saturated Fat", "Protein", "Sodium", "Potassium", "Cholesterol", "Total Carbohydrates", "Fiber", "Sugar"};
        try (CSVWriter writer = new CSVWriter(new FileWriter("nutrition_data.csv"))) {
            writer.writeNext(header);

            for (NutritionData nutritionData : nutritionDataList) {
                String[] line = {
                        nutritionData.getName(),
                        String.valueOf(nutritionData.getCalories()),
                        String.valueOf(nutritionData.getServingSize()),
                        String.valueOf(nutritionData.getTotalFat()),
                        String.valueOf(nutritionData.getSaturatedFat()),
                        String.valueOf(nutritionData.getProtein()),
                        String.valueOf(nutritionData.getSodium()),
                        String.valueOf(nutritionData.getPotassium()),
                        String.valueOf(nutritionData.getCholesterol()),
                        String.valueOf(nutritionData.getTotalCarbohydrates()),
                        String.valueOf(nutritionData.getFiber()),
                        String.valueOf(nutritionData.getSugar())
                };
                writer.writeNext(line);
            }
        }

        return nutritionDataList;
    }

    private void mergeSort(List<NutritionData> list) {
        if (list.size() > 1) {
            int meio = list.size() / 2;
            List<NutritionData> esquerda = new ArrayList<>(list.subList(0, meio));
            List<NutritionData> direita = new ArrayList<>(list.subList(meio, list.size()));

            mergeSort(esquerda);
            mergeSort(direita);

            int i = 0, j = 0, k = 0;
            while (i < esquerda.size() && j < direita.size()) {
                if (esquerda.get(i).getName().compareToIgnoreCase(direita.get(j).getName()) < 0) {
                    list.set(k++, esquerda.get(i++));
                } else {
                    list.set(k++, direita.get(j++));
                }
            }

            while (i < esquerda.size()) {
                list.set(k++, esquerda.get(i++));
            }

            while (j < direita.size()) {
                list.set(k++, direita.get(j++));
            }
        }
    }
}
