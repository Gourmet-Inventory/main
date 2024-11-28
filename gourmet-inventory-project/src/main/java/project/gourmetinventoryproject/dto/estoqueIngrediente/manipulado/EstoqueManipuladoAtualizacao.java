package project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteAtualizacaoDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueManipuladoAtualizacao {
    private EstoqueIngredienteAtualizacaoDto estoqueIngredienteAtualizacaoDto;
    List<IngredienteCriacaoDto> receita;
}
