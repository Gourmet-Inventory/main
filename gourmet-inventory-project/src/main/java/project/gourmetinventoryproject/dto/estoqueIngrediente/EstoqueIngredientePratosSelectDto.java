package project.gourmetinventoryproject.dto.estoqueIngrediente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueIngredientePratosSelectDto {
    private Long idItem;
    private String nome;
    private String lote;
    private String nomeConcatenado;

    public String getNomeConcatenado() {
        return String.format("%s - %s",this.nome, this.lote);
    }
}
