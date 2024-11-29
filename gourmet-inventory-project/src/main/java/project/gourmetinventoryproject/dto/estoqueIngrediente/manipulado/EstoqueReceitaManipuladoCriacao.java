package project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteCriacaoDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueReceitaManipuladoCriacao {
    private EstoqueIngredienteCriacaoDto estoqueIngredienteCriacaoDto;
    private List<IngredienteCriacaoDto> receita;
}
