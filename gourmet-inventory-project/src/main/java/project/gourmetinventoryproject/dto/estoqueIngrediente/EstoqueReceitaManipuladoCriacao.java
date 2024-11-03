package project.gourmetinventoryproject.dto.estoqueIngrediente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import project.gourmetinventoryproject.dto.receita.ReceitaCriacaoDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueReceitaManipuladoCriacao {
    private EstoqueIngredienteCriacaoDto estoqueIngredienteCriacaoDto;
    private ReceitaCriacaoDto receitaCriacaoDto;
}
