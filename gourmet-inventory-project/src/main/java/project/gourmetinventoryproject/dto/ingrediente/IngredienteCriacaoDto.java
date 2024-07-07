package project.gourmetinventoryproject.dto.ingrediente;

import lombok.AllArgsConstructor;import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;

@Getter
@Setter
@NoArgsConstructor
public class IngredienteCriacaoDto {
    private Long idItem;
    private Medidas tipoMedida;
    private Double valorMedida;
}

