package project.gourmetinventoryproject.dto.alerta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

@Getter
@Setter
@NoArgsConstructor
public class ItemListaComprasDto {
    private String nome;
    private Integer qtdMedia;
    private EstoqueIngrediente EstoqueIngrediente;
}
