package project.gourmetinventoryproject.controller.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NutricaoAPIController {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do alimento que deseja buscar: ");
        String food = scanner.nextLine();
        fetchNutritionData(food);
    }

    public static void fetchNutritionData(String food) throws IOException {
        String query = food.replace(" ", "%20");
        URL url = new URL("https://api.api-ninjas.com/v1/nutrition?query="+ query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("X-Api-Key", "HVxQ77fkJrXL5oz4yfNsPw==153XAxgTjQ7zcIyc");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);
        System.out.println(root);
    }
}
