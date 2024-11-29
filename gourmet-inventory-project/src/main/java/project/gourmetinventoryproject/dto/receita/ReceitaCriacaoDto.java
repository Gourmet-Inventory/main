package project.gourmetinventoryproject.dto.receita;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaCriacaoDto {
//    private Long idItem;
    @NotBlank
    private List<IngredienteCriacaoDto> receita;
}