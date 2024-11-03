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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.controller.EstoqueIngredienteController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/api")
public class NutricaoAPIController {

    @Autowired
    private EstoqueIngredienteController estoqueIngredienteController;

    private void mergeSort(List<List<Object>> arr, int l, int r) {
        if (l < r) {
            // Encontrar o ponto médio
            int m = l + (r - l) / 2;

            // Ordenar a primeira e a segunda metade
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Mesclar as partes ordenadas
            merge(arr, l, m, r);
        }
    }

    private void merge(List<List<Object>> arr, int l, int m, int r) {
        // Tamanhos dos subarrays a serem mesclados
        int n1 = m - l + 1;
        int n2 = r - m;

        // Arrays temporários
        List<List<Object>> L = new ArrayList<>(n1);
        List<List<Object>> R = new ArrayList<>(n2);

        // Copiar dados para arrays temporários L[] e R[]
        for (int i = 0; i < n1; ++i) {
            L.add(arr.get(l + i));
        }
        for (int j = 0; j < n2; ++j) {
            R.add(arr.get(m + 1 + j));
        }

        // Índices iniciais dos subarrays L[] e R[]
        int i = 0, j = 0;

        // Índice inicial do subarray mesclado
        int k = l;
        while (i < n1 && j < n2) {
            // Comparar os elementos L.get(i) e R.get(j) e colocar o menor em arr.get(k)
            if (((String) L.get(i).get(0)).compareTo((String) R.get(j).get(0)) <= 0) {
                arr.set(k, L.get(i));
                i++;
            } else {
                arr.set(k, R.get(j));
                j++;
            }
            k++;
        }

        // Copiar os elementos restantes de L[], se houver
        while (i < n1) {
            arr.set(k, L.get(i));
            i++;
            k++;
        }

        // Copiar os elementos restantes de R[], se houver
        while (j < n2) {
            arr.set(k, R.get(j));
            j++;
            k++;
        }
    }

//    @GetMapping("/consulta-nutricao-api/{idEmpresa}")
//    public ResponseEntity<byte[]> fetchNutritionDataFromAPI(@PathVariable Long idEmpresa) throws IOException {
//        ResponseEntity<List<EstoqueIngredienteConsultaDto>> responseEntity = estoqueIngredienteController.getAllEstoqueIngredientes(idEmpresa);
//        List<EstoqueIngredienteConsultaDto> estoqueIngredientes = responseEntity.getBody();
//
//        if (estoqueIngredientes == null || estoqueIngredientes.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        // Lista para armazenar os dados finais que serão usados para gerar o Excel
//        List<List<Object>> excelData = new ArrayList<>();
//
//        // Fila para processar os dados antes de adicioná-los à lista excelData
//        Queue<List<Object>> dataQueue = new LinkedList<>();
//
//        // Processar os dados obtidos da API e adicioná-los à fila
//        for (EstoqueIngredienteConsultaDto estoqueIngrediente : estoqueIngredientes) {
//            String query = estoqueIngrediente.getValorMedida() + "g%20" + estoqueIngrediente.getNome().replace(" ", "%20");
//            URL url = new URL("https://api.api-ninjas.com/v1/nutrition?query=" + query);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestProperty("accept", "application/json");
//            connection.setRequestProperty("X-Api-Key", "HVxQ77fkJrXL5oz4yfNsPw==153XAxgTjQ7zcIyc");
//
//            try (InputStream responseStream = connection.getInputStream()) {
//                ObjectMapper mapper = new ObjectMapper();
//                JsonNode root = mapper.readTree(responseStream);
//
//                for (JsonNode node : root) {
//                    String name = node.path("name").asText();
//                    double calories = node.path("calories").asDouble();
//                    double servingSize = node.path("serving_size_g").asDouble();
//                    double totalFat = node.path("fat_total_g").asDouble();
//                    double saturatedFat = node.path("fat_saturated_g").asDouble();
//                    double protein = node.path("protein_g").asDouble();
//                    int sodium = node.path("sodium_mg").asInt();
//                    int potassium = node.path("potassium_mg").asInt();
//                    int cholesterol = node.path("cholesterol_mg").asInt();
//                    double totalCarbohydrates = node.path("carbohydrates_total_g").asDouble();
//                    double fiber = node.path("fiber_g").asDouble();
//                    double sugar = node.path("sugar_g").asDouble();
//
//                    // Adicionar os dados à fila para processamento posterior
//                    List<Object> rowData = new ArrayList<>();
//                    rowData.add(name);
//                    rowData.add(calories);
//                    rowData.add(servingSize);
//                    rowData.add(totalFat);
//                    rowData.add(saturatedFat);
//                    rowData.add(protein);
//                    rowData.add(sodium);
//                    rowData.add(potassium);
//                    rowData.add(cholesterol);
//                    rowData.add(totalCarbohydrates);
//                    rowData.add(fiber);
//                    rowData.add(sugar);
//
//                    dataQueue.offer(rowData);
//                }
//            } catch (IOException e) {
//                // Tratar exceção adequadamente (logar, continuar ou interromper, dependendo do caso)
//                e.printStackTrace();
//                continue;
//            }
//        }
//
//        // Processar os dados da fila e adicionar à lista excelData
//        while (!dataQueue.isEmpty()) {
//            excelData.add(dataQueue.poll());
//        }
//
//        // Ordenar os dados usando Merge Sort
//        mergeSort(excelData, 0, excelData.size() - 1);
//
//        // Criar um arquivo Excel
//        byte[] excelBytes = createExcel(excelData);
//
//        // Definir headers para a resposta HTTP
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=nutrition_data.xlsx");
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//
//        // Retornar a resposta HTTP com o conteúdo do arquivo Excel
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(excelBytes);
//    }

    private byte[] createExcel(List<List<Object>> excelData) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Nutrition Data");

            // Cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] header = {"Nome Ingrediente/Prato", "Calorias", "Tamanho da Porção", "Gordura Total", "Gordura Saturada", "Proteína", "Sódio", "Potássio", "Colesterol", "Carboidratos Totais", "Fibra", "Açúcar"};
            for (int i = 0; i < header.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
            }

            // Dados
            int rowNum = 1;
            for (List<Object> rowData : excelData) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object field : rowData) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Double) {
                        cell.setCellValue((Double) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                    // Adicione mais verificações conforme necessário para outros tipos de dados
                }
            }

            workbook.write(outputStream);
        }

        return outputStream.toByteArray();
    }
}
