package project.gourmetinventoryproject.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NutricaoAPIController {

    @GetMapping("/consulta-nutricao")
    public List<EstoqueIngrediente> fetchNutritionData(@RequestParam String nomeIngrediente, @RequestParam String quantidadeGrama) throws IOException {
        String query = quantidadeGrama + "g%20" + nomeIngrediente.replace(" ", "%20");
        URL url = new URL("https://api.api-ninjas.com/v1/nutrition?query=" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("X-Api-Key", "HVxQ77fkJrXL5oz4yfNsPw==153XAxgTjQ7zcIyc");

        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);

        List<EstoqueIngrediente> estoqueIngredientes = new ArrayList<>();

        mergeSort(estoqueIngredientes);

        return estoqueIngredientes;
    }

    private void mergeSort(List<EstoqueIngrediente> estoqueIngredientes) {
        if (estoqueIngredientes.size() > 1) {
            int meio = estoqueIngredientes.size() / 2;
            List<EstoqueIngrediente> esquerda = new ArrayList<>(estoqueIngredientes.subList(0, meio));
            List<EstoqueIngrediente> direita = new ArrayList<>(estoqueIngredientes.subList(meio, estoqueIngredientes.size()));

            mergeSort(esquerda);
            mergeSort(direita);

            int i = 0, j = 0, k = 0;
            while (i < esquerda.size() && j < direita.size()) {
                if (esquerda.get(i).getNome().compareToIgnoreCase(direita.get(j).getNome()) < 0) {
                    estoqueIngredientes.set(k++, esquerda.get(i++));
                } else {
                    estoqueIngredientes.set(k++, direita.get(j++));
                }
            }

            while (i < esquerda.size()) {
                estoqueIngredientes.set(k++, esquerda.get(i++));
            }

            while (j < direita.size()) {
                estoqueIngredientes.set(k++, direita.get(j++));
            }
        }
    }
}
