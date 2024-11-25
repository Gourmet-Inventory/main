package project.gourmetinventoryproject.dto.ingrediente;

import lombok.*;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteCriacaoDto {
    private Long idItem;
    private Medidas tipoMedida;
    private Double valorMedida;
}

