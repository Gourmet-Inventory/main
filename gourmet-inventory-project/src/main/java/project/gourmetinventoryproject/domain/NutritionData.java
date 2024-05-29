package project.gourmetinventoryproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class NutritionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double calories;
    private double servingSize;
    private double totalFat;
    private double saturatedFat;
    private double protein;
    private int sodium;
    private int potassium;
    private int cholesterol;
    private double totalCarbohydrates;
    private double fiber;
    private double sugar;

    public NutritionData() {
    }

    public NutritionData(String name, double calories, double servingSize, double totalFat, double saturatedFat, double protein, int sodium, int potassium, int cholesterol, double totalCarbohydrates, double fiber, double sugar) {
        this.name = name;
        this.calories = calories;
        this.servingSize = servingSize;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.protein = protein;
        this.sodium = sodium;
        this.potassium = potassium;
        this.cholesterol = cholesterol;
        this.totalCarbohydrates = totalCarbohydrates;
        this.fiber = fiber;
        this.sugar = sugar;
    }
}
