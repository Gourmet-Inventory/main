package project.gourmetinventoryproject.dto.ingrediente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;

@Getter
@AllArgsConstructor
public class IngredienteCriacaoDto {
    private EstoqueIngrediente estoqueIngrediente;
    private Medidas tipoMedida;
    private String valorMedida;
}

